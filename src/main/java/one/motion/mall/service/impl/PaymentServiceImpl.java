package one.motion.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import one.motion.mall.dto.Currency;
import one.motion.mall.dto.PayType;
import one.motion.mall.dto.PaymentResult;
import one.motion.mall.dto.PaymentStatus;
import one.motion.mall.mapper.MallOrderMapper;
import one.motion.mall.model.MallOrder;
import one.motion.mall.service.IPaymentService;
import one.motion.mall.utils.ShbUtils;
import one.motion.proto.wallet.model.ExpendMsgProto;
import one.motion.proto.wallet.model.ExpendResultProto;
import one.motion.proto.wallet.service.MtnServiceGrpc;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PaymentServiceImpl implements IPaymentService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MallOrderMapper orderMapper;
    private final RestTemplate restTemplate;

    @Value("${SHB_MERCHANT_NUMBER:}")
    private String SHB_MERCHANT_NUMBER;

    public PaymentServiceImpl(MallOrderMapper orderMapper, RestTemplate restTemplate) {
        this.orderMapper = orderMapper;
        this.restTemplate = restTemplate;
    }

    @GrpcClient("wallet-service")
    private Channel walletServiceChannel;

    @Value("${MOTION_WALLET_SERVICE_ADDR:}")
    private String walletAddress;

    @Override
    public PaymentResult mtnPay(MallOrder order) {
        if (order == null || order.getPayStatus() == null || !order.getPayStatus().equals(PaymentStatus.UNPAID.getCode().byteValue())) {
            throw new RuntimeException("order_status_error");
        }
        return payWithMTN(order.getOrderId(), new BigDecimal(order.getMtnAmount()), order.getUserId());
    }

    @Override
    public PaymentResult cashPay(MallOrder order, PayType payType) {
        if (payType == PayType.SHB) {

        }
        return new PaymentResult();
    }

    private PaymentResult payWithMTN(String orderId, BigDecimal amount, Long userId) {
        MtnServiceGrpc.MtnServiceBlockingStub stub = MtnServiceGrpc.newBlockingStub(walletServiceChannel);
        ExpendMsgProto request = ExpendMsgProto.newBuilder()
                .setMtn(amount.toString())
                .setType(1)
                .setOrderId(orderId)
                .setUserId(userId)
                .build();
        ExpendResultProto result = stub.saveMtnExpend(request);
        String codeName = result.getResultCode().name();
        int code = result.getResultCode().getNumber();
        String message = result.getResultMsg();
        logger.info("Send transaction result: {}:{}, {}", code, codeName, message);
        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setPaymentOrderId(orderId);
        paymentResult.setOrderId(orderId);
        paymentResult.setAmount(amount);
        paymentResult.setCurrency(Currency.MTN);
        paymentResult.setResultCode(String.valueOf(code));
        paymentResult.setResultMessage(message);
        paymentResult.setTime(new Date());
        paymentResult.setUserId(userId);
        if (code == 200) {
            paymentResult.setStatus(PaymentStatus.PAID);
        } else {
            paymentResult.setStatus(PaymentStatus.PAY_FAIL);
        }
        return paymentResult;
    }

    @Override
    public BigDecimal getMtnValue(BigDecimal amount, String currency) {
        try {
            String url = "http://" + walletAddress + ":8080/oapi/v1/wallet/exchange?amount=" + amount + "&currency" + currency;
            JSONObject result = restTemplate.getForObject(url, JSONObject.class);
            if (result == null) {
                return BigDecimal.ZERO;
            }
            String code = result.getString("code");
            if (!StringUtils.equals(code, "200")) {
                return BigDecimal.ZERO;
            }
            String mtnStr = result.getJSONObject("data").getString("mtn");
            try {
                return new BigDecimal(mtnStr);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return BigDecimal.ZERO;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    @Override
    public PaymentResult processPaymentNotify(String data, PayType payType) {
        if (payType == null) {
            throw new RuntimeException("unknown_payType_error");
        }
        if (PayType.SHB == payType) {
            return shbResult(data);
        }
        return new PaymentResult();
    }

    private PaymentResult shbResult(String data) {
        PaymentResult paymentResult = new PaymentResult();
        JSONObject requestJson = JSONObject.parseObject(data);
        JSONObject jsonObject = ShbUtils.decodeContext(requestJson.getString("context"));
        if (jsonObject == null) {
            throw new RuntimeException("decode_context_error");
        }
        JSONObject head = jsonObject.getJSONObject("businessHead");
        logger.error("shb异步通知报文,解密后,head:{} ", head);
        JSONObject context = jsonObject.getJSONObject("businessContext");
        logger.error("shb异步通知报文,解密后,context:{} ", context);

        if (ShbUtils.verify(jsonObject)) {
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
        String payBank = context.get("payBank") == null ? "" : context.getString("payBank");
        String paymentTime = context.get("paymentTime") == null ? "" : context.getString("paymentTime");
        paymentResult.setOrderId(merchantOrderNumber);
        paymentResult.setPaymentOrderId(shbOrderNumber);
        try {
            paymentResult.setTime(DateUtils.parseDate(paymentTime, "yyyyMMddHHmmss"));
        } catch (ParseException e) {
            paymentResult.setTime(new Date());
        }
        paymentResult.setCurrency(Currency.CNY);
        paymentResult.setAmount(BigDecimal.valueOf(Integer.valueOf(tradeAmount) / 100.0));
        paymentResult.setResultCode(orderStatus);
        paymentResult.setResultMessage(data);
        return paymentResult;
    }

    public JSONObject buildBusinessHead() {
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

    public JSONObject buildBusinessContextFormPay(String shbPayType, BigDecimal amount, String productInfo, String productDetail, String remark) {
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
