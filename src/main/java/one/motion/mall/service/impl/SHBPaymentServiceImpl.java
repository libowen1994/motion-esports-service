package one.motion.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import one.motion.mall.dto.Currency;
import one.motion.mall.dto.PayChannel;
import one.motion.mall.dto.PaymentResult;
import one.motion.mall.dto.PaymentStatus;
import one.motion.mall.mapper.MallOrderMapper;
import one.motion.mall.model.MallOrder;
import one.motion.mall.service.IPaymentService;
import one.motion.mall.utils.RSAUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("shbPaymentService")
public class SHBPaymentServiceImpl implements IPaymentService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MallOrderMapper orderMapper;
    private final RestTemplate restTemplate;

    @Value("${SHB_MERCHANT_NUMBER}")
    private String SHB_MERCHANT_NUMBER;

    @Value("${SHB_PAYMENT_BASE_URL}")
    private String SHB_PAYMENT_BASE_URL;
    @GrpcClient("wallet-service")
    private Channel walletServiceChannel;
    @Value("${MOTION_WALLET_SERVICE_ADDR:}")
    private String walletAddress;
    @Value("${PAYMENT_DOMAIN}")
    private String paymentGatewayDomain;
    @Value("${SHB_RSA_PUB_KEY}")
    private String shbPublicKeyString;
    @Value("${MTN_RSA_PRI_KEY}")
    private String privateKeyString;
    @Value("${MTN_RSA_PUB_KEY}")
    private String publicKeyString;

    public SHBPaymentServiceImpl(MallOrderMapper orderMapper, RestTemplate restTemplate) {
        this.orderMapper = orderMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public PaymentResult toPay(String orderId, PayChannel channel) {
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        if (order == null) {
            throw new RuntimeException("unknown_order_error");
        }
        if (order.getPayStatus() == null || !order.getPayStatus().equals(PaymentStatus.UNPAID.getCode().byteValue())) {
            throw new RuntimeException("order_status_error");
        }
        String payType = "";
        if (channel == PayChannel.ALIPAY) {
            payType = "ALI_NATIVE";
        }
        if (channel == PayChannel.WECHAT) {
            payType = "WECHAT_NATIVE";
        }
        JSONObject businessHead = buildBusinessHead();

//        JSONObject businessContext = buildBusinessContextFormPay(orderId, payType, BigDecimal.valueOf(order.getTotalAmount()),
//                order.getProductName(), order.getProductId(), order.getCategoryCode(), order.getIpAddress(),
//                order.getUserId());
        JSONObject businessContext = buildBusinessContextFormPay();
        String context = null;
        try {
            context = RSAUtils.verifyAndEncryptionToString(businessContext, businessHead, privateKeyString, shbPublicKeyString);
        } catch (Exception e) {
            throw new RuntimeException("sign_error");
        }
        JSONObject toPost = new JSONObject(1);
        toPost.put("context", context);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(toPost.toJSONString(), headers);
        ResponseEntity<JSONObject> response = restTemplate.exchange(SHB_PAYMENT_BASE_URL + "/api/unified/payment", HttpMethod.POST, entity, JSONObject.class);
        PaymentResult paymentResult = new PaymentResult();
        JSONObject responseJSON = response.getBody();
        if (response.getStatusCode() != HttpStatus.OK || responseJSON == null) {
            paymentResult.setStatus(PaymentStatus.PAY_FAIL);
            paymentResult.setResultCode("HTTP:" + response.getStatusCodeValue());
            paymentResult.setResultMessage(response.toString());
            return paymentResult;
        }
        JSONObject responseMessage = responseJSON.getJSONObject("message");

        if (!responseJSON.getBoolean("success")) {
            paymentResult.setStatus(PaymentStatus.PAY_FAIL);
            paymentResult.setResultCode(responseMessage.getString("code"));
            paymentResult.setResultMessage(responseMessage.getString("content"));
            return paymentResult;
        }
        JSONObject responseData = decodeContext(responseJSON.getString("context"));
        if (responseData == null) {
            throw new RuntimeException("shb_decode_error");
        }
        if (!verify(responseData)) {
            throw new RuntimeException("shb_verify_error");
        }
        businessContext = responseData.getJSONObject("businessContext");
        paymentResult.setPaymentOrderId(businessContext.getString("shbOrderNumber"));
        paymentResult.setOrderId(orderId);
        paymentResult.setResultCode(businessContext.getString("orderStatus"));
        paymentResult.setResultMessage(businessContext.toJSONString());
        paymentResult.setTime(new Date());
        paymentResult.setUrl(businessContext.getString("content"));
        paymentResult.setUserId(order.getUserId());
        if ("WAIT".equals(businessContext.getString("orderStatus"))) {
            paymentResult.setStatus(PaymentStatus.IN_PAY);
        } else {
            paymentResult.setStatus(PaymentStatus.PAY_FAIL);
        }
        return paymentResult;
    }

    @Override
    public PaymentResult processPaymentNotify(String data) {
        PaymentResult paymentResult = new PaymentResult();
        JSONObject requestJson = JSONObject.parseObject(data);
        JSONObject jsonObject = decodeContext(requestJson.getString("context"));
        if (jsonObject == null) {
            throw new RuntimeException("decode_context_error");
        }
        JSONObject head = jsonObject.getJSONObject("businessHead");
        logger.error("shb异步通知报文,解密后,head:{} ", head);
        JSONObject context = jsonObject.getJSONObject("businessContext");
        logger.error("shb异步通知报文,解密后,context:{} ", context);

        if (verify(jsonObject)) {
            throw new RuntimeException("sign_verify_error");
        }
        String orderStatus = context.get("orderStatus") == null ? "" : context.getString("orderStatus");
        if ("SUC".equals(orderStatus)) {
            paymentResult.setStatus(PaymentStatus.PAID);
        } else if ("ERROR".equals(orderStatus) || "CLOSE".equals(orderStatus)) {
            paymentResult.setStatus(PaymentStatus.PAY_FAIL);
        } else {
            paymentResult.setStatus(PaymentStatus.IN_PAY);
        }
        String merchantOrderNumber = context.get("subMerchantNumber") == null ? "" : context.getString("subMerchantNumber");
        String shbOrderNumber = context.get("shbOrderNumber") == null ? "" : context.getString("shbOrderNumber");
        String currenciesType = context.get("currenciesType") == null ? "" : context.getString("currenciesType");
        String tradeAmount = context.get("tradeAmount") == null ? "" : context.getString("tradeAmount");//分
        String fee = context.get("tradeFee") == null ? "" : context.getString("tradeFee");//分
        String paymentTime = context.get("paymentTime") == null ? "" : context.getString("paymentTime");
        paymentResult.setOrderId(merchantOrderNumber);
        paymentResult.setPaymentOrderId(shbOrderNumber);
        try {
            paymentResult.setTime(DateUtils.parseDate(paymentTime, "yyyyMMddHHmmss"));
        } catch (ParseException e) {
            paymentResult.setTime(new Date());
        }
        paymentResult.setCurrency(Currency.valueOf(currenciesType));
        paymentResult.setTotalAmount(BigDecimal.valueOf(Integer.valueOf(tradeAmount) / 100.0));
        paymentResult.setFee(BigDecimal.valueOf(Integer.valueOf(fee) / 100.0));
        paymentResult.setResultCode(orderStatus);
        paymentResult.setResultMessage(data);
        return paymentResult;
    }

    @Override
    public PaymentResult queryPaymentStatus(String orderId) {
        JSONObject businessHead = buildBusinessHead();
        JSONObject businessContext = new JSONObject(2);
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        if (order == null) {
            throw new RuntimeException("unknown_order_error");
        }
        businessContext.put("merchantOrderNumber", order.getOrderId());
        businessContext.put("shbOrderNumber", order.getPaymentOrderId());
        String context = null;
        try {
            context = RSAUtils.verifyAndEncryptionToString(businessContext, businessHead, privateKeyString, shbPublicKeyString);
        } catch (Exception e) {
            throw new RuntimeException("sign_error");
        }
        JSONObject toPost = new JSONObject(1);
        toPost.put("context", context);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(toPost.toJSONString(), headers);
        ResponseEntity<JSONObject> response = restTemplate.exchange(SHB_PAYMENT_BASE_URL + "/api/query/tradeOrder", HttpMethod.POST, entity, JSONObject.class);
        PaymentResult paymentResult = new PaymentResult();
        JSONObject responseJSON = response.getBody();
        if (response.getStatusCode() != HttpStatus.OK || responseJSON == null) {
            paymentResult.setStatus(PaymentStatus.PAY_FAIL);
            paymentResult.setResultCode("HTTP:" + response.getStatusCodeValue());
            paymentResult.setResultMessage(response.toString());
            return paymentResult;
        }
        JSONObject responseMessage = responseJSON.getJSONObject("message");
        if (!responseJSON.getBoolean("success")) {
            paymentResult.setStatus(PaymentStatus.PAY_FAIL);
            paymentResult.setResultCode(responseMessage.getString("code"));
            paymentResult.setResultMessage(responseMessage.getString("content"));
            return paymentResult;
        }
        JSONObject responseData = decodeContext(responseJSON.getString("context"));
        if (responseData == null) {
            throw new RuntimeException("shb_decode_error");
        }
        if (!verify(responseData)) {
            throw new RuntimeException("shb_verify_error");
        }
        businessContext = responseData.getJSONObject("businessContext");
        paymentResult.setPaymentOrderId(businessContext.getString("shbOrderNumber"));
        paymentResult.setOrderId(orderId);
        paymentResult.setResultCode(businessContext.getString("orderStatus"));
        paymentResult.setResultMessage(businessContext.toJSONString());
        paymentResult.setTime(new Date());
        paymentResult.setUrl(businessContext.getString("content"));
        paymentResult.setUserId(order.getUserId());
        if ("WAIT".equals(businessContext.getString("orderStatus"))) {
            paymentResult.setStatus(PaymentStatus.IN_PAY);
        } else {
            paymentResult.setStatus(PaymentStatus.PAY_FAIL);
        }
        return paymentResult;
    }

    private JSONObject buildBusinessHead() {
        JSONObject businessHead = new JSONObject();
        businessHead.put("charset", "00");//字符集 00 : utf-8
        businessHead.put("version", "V1.0.0");//版本号
        businessHead.put("method", "payment");//方法
        businessHead.put("merchantNumber", SHB_MERCHANT_NUMBER);//商户号
        businessHead.put("requestTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));//请求时间
        businessHead.put("signType", "RSA");//签名类型
        businessHead.put("sign", "");//签名
        return businessHead;
    }

    private JSONObject buildBusinessContextFormPay(String orderId, String shbPayType, BigDecimal amount, String productInfo, String productDetail, String remark, String ip, Long userId) {
        JSONObject businessContext = new JSONObject();
        businessContext.put("defrayalType", shbPayType);//支付方式(必填)
        businessContext.put("subMerchantNumber", SHB_MERCHANT_NUMBER);//商户号(必填)
        businessContext.put("channelMapped", "HB_CIB_ONLINE");//支付通道标识(必填)
        businessContext.put("merchantOrderNumber", System.currentTimeMillis());//商户订单号(必填),每次交易唯一
        businessContext.put("tradeCheckCycle", "T1");//结算周期(必填)
        businessContext.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));//订单时间(必填)
        businessContext.put("currenciesType", "CNY");//币种：人民币(必填)
        businessContext.put("tradeAmount", amount.multiply(BigDecimal.valueOf(100)));//交易金额分(必填)

        businessContext.put("commodityBody", productInfo);//商品信息(必填)
        businessContext.put("commodityDetail", productDetail);//商品详情(必填)
        businessContext.put("commodityRemark", remark);//商品备注

        businessContext.put("returnUrl", paymentGatewayDomain + "/mall/payment/" + orderId);//同步通知
        businessContext.put("notifyUrl", "https://" + paymentGatewayDomain + "/api/v1/mall/payment/notify/shb");//异步通知(必填)

        businessContext.put("terminalId", ip + userId);//设备ID
        businessContext.put("terminalIP", ip);//设备IP(必填)
        businessContext.put("userId", userId);//用户标示,ALI_SCAN/WECHAT_SCAN必填

        businessContext.put("remark", remark);//备注
        businessContext.put("attach", "");//附加信息
        return businessContext;
    }

    private JSONObject decodeContext(String context) {
        String decodedContext;
        try {
            decodedContext = RSAUtils.decryptByPrivateKey(context, privateKeyString);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        return JSON.parseObject(decodedContext);
    }

    private boolean verify(JSONObject data) {
        try {
            return RSAUtils.verify(data.toJSONString(), shbPublicKeyString);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 支付请求内容
     *
     * @return
     */
    public JSONObject buildBusinessContextFormPay() {
        JSONObject businessContext = new JSONObject();
        businessContext.put("defrayalType", "ALI_H5");//支付方式(必填)
        businessContext.put("subMerchantNumber", SHB_MERCHANT_NUMBER);//商户号(必填)
        businessContext.put("channelMapped", "HB_CIB_ONLINE");//支付通道标识(必填)
        businessContext.put("merchantOrderNumber", System.currentTimeMillis());//商户订单号(必填),每次交易唯一
        businessContext.put("tradeCheckCycle", "T1");//结算周期(必填)
        businessContext.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));//订单时间(必填)
        businessContext.put("currenciesType", "CNY");//币种：人民币(必填)
        businessContext.put("tradeAmount", "1");//交易金额分(必填)

        businessContext.put("commodityBody", "商品信息");//商品信息(必填)
        businessContext.put("commodityDetail", "商品详情");//商品详情(必填)
        businessContext.put("commodityRemark", "商品备注");//商品备注

        businessContext.put("returnUrl", "http://www.baidu.com");//同步通知
        businessContext.put("notifyUrl", "http://39.108.134.13:10015/api/notify/shb");//异步通知(必填)

        businessContext.put("terminalId", "设备ID");//设备ID
        businessContext.put("terminalIP", "8.8.8.8");//设备IP(必填)
        businessContext.put("userId", "123");//用户标示,ALI_SCAN/WECHAT_SCAN必填

        businessContext.put("remark", "备注");//备注
        businessContext.put("attach", "");//附加信息
        return businessContext;
    }
}
