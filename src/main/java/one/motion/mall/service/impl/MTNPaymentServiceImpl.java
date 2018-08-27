package one.motion.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import one.motion.mall.dto.Currency;
import one.motion.mall.dto.PayChannel;
import one.motion.mall.dto.PaymentResult;
import one.motion.mall.dto.PaymentStatus;
import one.motion.mall.mapper.MallOrderMapper;
import one.motion.mall.model.MallOrder;
import one.motion.mall.service.IPaymentService;
import one.motion.mall.service.IWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service("mtnPaymentService")
public class MTNPaymentServiceImpl implements IPaymentService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MallOrderMapper orderMapper;
    private final IWalletService walletService;

    public MTNPaymentServiceImpl(MallOrderMapper orderMapper, IWalletService walletService) {
        this.orderMapper = orderMapper;
        this.walletService = walletService;
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
        JSONObject json = walletService.expendMTN(BigDecimal.valueOf(order.getMtnAmount()), order.getOrderId(), order.getUserId());
        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setPaymentOrderId(orderId);
        paymentResult.setOrderId(orderId);
        paymentResult.setTotalAmount(BigDecimal.valueOf(order.getMtnAmount()));
        paymentResult.setCurrency(Currency.MTN);
        paymentResult.setResultCode(json.getString("code"));
        paymentResult.setResultMessage(json.getString("message"));
        paymentResult.setTime(new Date());
        paymentResult.setUserId(order.getUserId());
        if ("200".equals(json.getString("code"))) {
            paymentResult.setStatus(PaymentStatus.PAID);
        } else {
            paymentResult.setStatus(PaymentStatus.PAY_FAIL);
        }
        return paymentResult;
    }

    @Override
    public PaymentResult processPaymentNotify(String data) {
        return null;
    }
}
