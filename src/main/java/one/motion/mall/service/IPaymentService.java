package one.motion.mall.service;

import one.motion.mall.dto.PayType;
import one.motion.mall.dto.PaymentResult;

import java.math.BigDecimal;

public interface IPaymentService {

    PaymentResult mtnPay(String orderId);

    PaymentResult cashPay(String orderId);

    BigDecimal getMtnValue(BigDecimal amount, String currency);

    PaymentResult processPaymentNotify(String data, PayType payType);

}
