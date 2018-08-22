package one.motion.mall.service;

import one.motion.mall.dto.Currency;

import java.math.BigDecimal;

public interface IPaymentService {

    boolean pay(String orderId, BigDecimal amount, Currency currency);

    BigDecimal getMtnValue(BigDecimal amount, String currency);

}
