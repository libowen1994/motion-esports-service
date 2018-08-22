package one.motion.mall.service;

import com.alibaba.fastjson.JSONObject;
import one.motion.mall.dto.Currency;

import java.math.BigDecimal;

public interface IPaymentService {

    JSONObject pay(String orderId, BigDecimal amount, Currency currency);

    BigDecimal getMtnValue(BigDecimal amount, String currency);

}
