package one.motion.mall.service.impl;

import cn.com.ips.payat.WebService.OrderQuery.OrderQueryService;
import cn.com.ips.payat.WebService.OrderQuery.WSOrderQueryLocator;
import cn.com.ips.payat.WebService.Scan.ScanService;
import cn.com.ips.payat.WebService.Scan.WSScanLocator;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import one.motion.mall.dto.PayChannel;
import one.motion.mall.dto.PaymentResult;
import one.motion.mall.dto.PaymentStatus;
import one.motion.mall.ips.*;
import one.motion.mall.mapper.MallOrderMapper;
import one.motion.mall.model.MallOrder;
import one.motion.mall.service.IPaymentService;
import one.motion.mall.utils.XMLUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("ipsPaymentService")
public class IPSPaymentServiceImpl implements IPaymentService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MallOrderMapper orderMapper;

    @Value("${IPS_MER_CODE}")
    private String IPS_MER_CODE;

    @Value("${IPS_MER_NAME}")
    private String IPS_MER_NAME;

    @Value("${IPS_MER_ACCOUNT}")
    private String IPS_MER_ACCOUNT;

    @Value("${IPS_MD5_KEY}")
    private String IPS_MD5_KEY;

    @Value("${PAYMENT_DOMAIN}")
    private String PAYMENT_DOMAIN;


    public IPSPaymentServiceImpl(MallOrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @GrpcClient("wallet-service")
    private Channel walletServiceChannel;

    @Value("${MOTION_WALLET_SERVICE_ADDR:}")
    private String walletAddress;

    @Override
    public PaymentResult toPay(String orderId, PayChannel channel) {
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        if (order == null) {
            throw new RuntimeException("unknown_order_error");
        }
        if (order.getPayStatus() == null
                || !order.getPayStatus().equals(PaymentStatus.UNPAID.getCode().byteValue())) {
            throw new RuntimeException("order_status_error");
        }
        String payType = "";
        if (channel == PayChannel.ALIPAY) {
            payType = "11";
        }
        if (channel == PayChannel.WECHAT) {
            payType = "10";
        }
        String messageId = System.currentTimeMillis() + "";
        ScanPayReq req = new ScanPayReq();
        ScanPayGateWayReq gateWayReq = new ScanPayGateWayReq();
        RequestHead head = new RequestHead();
        head.setAccount(IPS_MER_ACCOUNT);
        head.setMerCode(IPS_MER_CODE);
        head.setMerName(IPS_MER_NAME);
        head.setVersion("v1.0.1");
        head.setMsgId(messageId);
        head.setReqDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        ScanPayReqBody reqBody = new ScanPayReqBody();
        reqBody.setMerBillNo(orderId);
        reqBody.setGatewayType(payType);
        reqBody.setMerType("0");
        reqBody.setDate(new SimpleDateFormat("yyyyMMdd").format(order.getCreatedAt()));
        reqBody.setCurrencyType("156");
        reqBody.setAmount(BigDecimal.valueOf(order.getTotalAmount()).setScale(2, RoundingMode.HALF_UP).toString());
        reqBody.setSpbillCreateIp(order.getIpAddress());
        reqBody.setRetEncodeType("17");
        reqBody.setServerUrl(PAYMENT_DOMAIN + "/api/v1/mall/payment/notify/ips");
        reqBody.setGoodsName(order.getProductName());
        String body = XMLUtil.convertToXml(reqBody);
        String sign = getSign(body);
        head.setSignature(sign);
        gateWayReq.setBody(reqBody);
        gateWayReq.setHead(head);
        req.setReq(gateWayReq);
        String scanPayReq = XMLUtil.convertToXml(req);
        WSScanLocator locator = new WSScanLocator();
        PaymentResult paymentResult = new PaymentResult();
        try {
            ScanService scanService = locator.getWSScanSoap();
            String result = scanService.scanPay(scanPayReq);
            ScanPayResp resp = XMLUtil.convertXmlStrToObject(ScanPayResp.class, result);
            if (resp == null
                    || resp.getReq() == null
                    || resp.getReq().getBody() == null
                    || resp.getReq().getHead() == null) {
                throw new RuntimeException("ips_response_invalid_error");
            }
            ResponseHead responseHead = resp.getReq().getHead();
            ScanPayRespBody respBody = resp.getReq().getBody();
            String responseBody = XMLUtil.convertToXml(respBody);
            String toSign = getSign(responseBody);
            String responseSign = responseHead.getSignature();
            if (!StringUtils.equals(responseSign, toSign)) {
                throw new RuntimeException("ips_response_sign_error");
            }
            if (!StringUtils.equals(responseHead.getReferenceID(), messageId)) {
                throw new RuntimeException("ips_response_invalid_messageId_error");
            }
            if (!StringUtils.equals(responseHead.getRspCode(), "000000")) {
                paymentResult.setStatus(PaymentStatus.PAY_FAIL);
            } else {
                paymentResult.setStatus(PaymentStatus.IN_PAY);
                paymentResult.setUrl(respBody.getQrCode());
            }
            paymentResult.setOrderId(orderId);
            paymentResult.setResultCode(responseHead.getRspCode());
            paymentResult.setResultMessage(responseHead.getRspMsg());
            paymentResult.setTime(new Date());
            paymentResult.setUserId(order.getUserId());
            return paymentResult;
        } catch (ServiceException | RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("ips_request_error");
        }
    }

    private String getSign(String data) {
        if (StringUtils.isBlank(data)) {
            return null;
        }
        return DigestUtils.md5Hex(data + IPS_MER_CODE + IPS_MD5_KEY).toLowerCase();
    }

    @Override
    public PaymentResult processPaymentNotify(String data) {
        ScanPayNotify resp = XMLUtil.convertXmlStrToObject(ScanPayNotify.class, data);
        PaymentResult paymentResult = new PaymentResult();
        if (resp == null
                || resp.getReq() == null
                || resp.getReq().getBody() == null
                || resp.getReq().getHead() == null) {
            throw new RuntimeException("ips_response_invalid_error");
        }
        ResponseHead responseHead = resp.getReq().getHead();
        ScanPayNotifyBody respBody = resp.getReq().getBody();
        String responseBody = XMLUtil.convertToXml(respBody);
        String toSign = getSign(responseBody);
        String responseSign = responseHead.getSignature();
        if (!StringUtils.equals(responseSign, toSign)) {
            throw new RuntimeException("ips_response_sign_error");
        }
        if (!StringUtils.equals(responseHead.getRspCode(), "000000")) {
            paymentResult.setResultCode(responseHead.getRspCode());
            paymentResult.setResultMessage(responseHead.getRspMsg());
            return paymentResult;
        }
        paymentResult.setPaymentOrderId(respBody.getIpsBillNo());
        paymentResult.setOrderId(respBody.getMerBillNo());
        paymentResult.setResultCode(responseHead.getRspCode());
        paymentResult.setResultMessage(responseHead.getRspMsg());
        paymentResult.setTotalAmount(BigDecimal.valueOf(Double.valueOf(respBody.getAmount())));
        if (StringUtils.equals(respBody.getStatus(), "Y")) {
            paymentResult.setStatus(PaymentStatus.PAID);
        }
        if (StringUtils.equals(respBody.getStatus(), "N")) {
            paymentResult.setStatus(PaymentStatus.PAY_FAIL);
        }
        if (StringUtils.equals(respBody.getStatus(), "P")) {
            paymentResult.setStatus(PaymentStatus.IN_PAY);
        }
        try {
            paymentResult.setTime(DateUtils.parseDate(respBody.getDate(), "yyyyMMdd"));
        } catch (ParseException e) {
            paymentResult.setTime(new Date());
        }
        return paymentResult;
    }

    @Override
    public PaymentResult queryPaymentStatus(String orderId) {
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        if (order == null) {
            throw new RuntimeException("unknown_order_error");
        }
        String messageId = System.currentTimeMillis() + "";
        OrderQuery query = new OrderQuery();
        OrderQueryReq req = new OrderQueryReq();
        RequestHead head = new RequestHead();
        head.setAccount(IPS_MER_ACCOUNT);
        head.setMerCode(IPS_MER_CODE);
        head.setMerName(IPS_MER_NAME);
        head.setVersion("v1.0.1");
        head.setMsgId(messageId);
        head.setReqDate(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        OrderQueryReqBody reqBody = new OrderQueryReqBody();
        reqBody.setMerBillNo(orderId);
        reqBody.setDate(new SimpleDateFormat("yyyyMMdd").format(order.getCreatedAt()));
        reqBody.setAmount(BigDecimal.valueOf(order.getTotalAmount()).setScale(2, RoundingMode.HALF_UP).toString());
        String body = XMLUtil.convertToXml(reqBody);
        String sign = getSign(body);
        head.setSignature(sign);
        req.setBody(reqBody);
        req.setHead(head);
        query.setReq(req);
        String orderQuery = XMLUtil.convertToXml(req);
        WSOrderQueryLocator locator = new WSOrderQueryLocator();
        PaymentResult paymentResult = new PaymentResult();
        try {
            OrderQueryService queryService = locator.getWSOrderQuerySoap();
            String result = queryService.getOrderByMerBillNo(orderQuery);
            OrderQueryResp resp = XMLUtil.convertXmlStrToObject(OrderQueryResp.class, result);
            if (resp == null
                    || resp.getReq() == null
                    || resp.getReq().getBody() == null
                    || resp.getReq().getHead() == null) {
                throw new RuntimeException("ips_response_invalid_error");
            }
            ResponseHead responseHead = resp.getReq().getHead();
            OrderQueryRespBody respBody = resp.getReq().getBody();
            String responseBody = XMLUtil.convertToXml(respBody);
            String toSign = getSign(responseBody);
            String responseSign = responseHead.getSignature();
            if (!StringUtils.equals(responseSign, toSign)) {
                throw new RuntimeException("ips_response_sign_error");
            }
            if (!StringUtils.equals(responseHead.getReferenceID(), messageId)) {
                throw new RuntimeException("ips_response_invalid_messageId_error");
            }
            if (!StringUtils.equals(responseHead.getRspCode(), "000000")) {
                paymentResult.setOrderId(orderId);
                paymentResult.setResultCode(responseHead.getRspCode());
                paymentResult.setResultMessage(responseHead.getRspMsg());
                return paymentResult;
            }
            paymentResult.setPaymentOrderId(respBody.getIpsBillNo());
            paymentResult.setOrderId(respBody.getMerBillNo());
            paymentResult.setResultCode(responseHead.getRspCode());
            paymentResult.setResultMessage(responseHead.getRspMsg());
            paymentResult.setTotalAmount(BigDecimal.valueOf(Double.valueOf(respBody.getAmount())));
            if (StringUtils.equals(respBody.getStatus(), "Y")) {
                paymentResult.setStatus(PaymentStatus.PAID);
            }
            if (StringUtils.equals(respBody.getStatus(), "N")) {
                paymentResult.setStatus(PaymentStatus.PAY_FAIL);
            }
            if (StringUtils.equals(respBody.getStatus(), "P")) {
                paymentResult.setStatus(PaymentStatus.IN_PAY);
            }
            try {
                paymentResult.setTime(DateUtils.parseDate(respBody.getMerBillDate(), "yyyyMMdd"));
            } catch (ParseException e) {
                paymentResult.setTime(new Date());
            }
            return paymentResult;
        } catch (ServiceException | RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("ips_request_error");
        }
    }
}
