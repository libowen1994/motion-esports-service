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
import one.motion.proto.wallet.model.ExpendMsgProto;
import one.motion.proto.wallet.model.ExpendResultProto;
import one.motion.proto.wallet.service.MtnServiceGrpc;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class PaymentServiceImpl implements IPaymentService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MallOrderMapper orderMapper;
    private final RestTemplate restTemplate;

    public PaymentServiceImpl(MallOrderMapper orderMapper, RestTemplate restTemplate) {
        this.orderMapper = orderMapper;
        this.restTemplate = restTemplate;
    }

    @GrpcClient("wallet-service")
    private Channel walletServiceChannel;

    @Value("${MOTION_WALLET_SERVICE_ADDR:}")
    private String walletAddress;

    @Override
    public PaymentResult mtnPay(String orderId) {
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        if (order == null || order.getPayStatus() == null || !order.getPayStatus().equals(PaymentStatus.UNPAID.getCode().byteValue())) {
            throw new RuntimeException("order_status_error");
        }
        return payWithMTN(order.getOrderId(), new BigDecimal(order.getMtnAmount()), order.getUserId());
    }

    @Override
    public PaymentResult cashPay(String orderId) {
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

        }
        return new PaymentResult();
    }
}
