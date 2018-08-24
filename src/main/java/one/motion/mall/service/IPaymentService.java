package one.motion.mall.service;

import one.motion.mall.dto.PayType;
import one.motion.mall.dto.PaymentResult;
import one.motion.mall.model.MallOrder;

import java.math.BigDecimal;

public interface IPaymentService {

    PaymentResult mtnPay(MallOrder order);

    PaymentResult cashPay(MallOrder order, PayType payType);

    BigDecimal getMtnValue(BigDecimal amount, String currency);

    PaymentResult processPaymentNotify(String data, PayType payType);

}
