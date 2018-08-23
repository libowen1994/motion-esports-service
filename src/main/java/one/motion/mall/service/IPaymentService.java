package one.motion.mall.service;

import com.alibaba.fastjson.JSONObject;
import one.motion.mall.dto.PaymentResult;

import java.math.BigDecimal;

public interface IPaymentService {

    PaymentResult mtnPay(String orderId);

    PaymentResult ipsPay(String orderId);

    BigDecimal getMtnValue(BigDecimal amount, String currency);

    PaymentResult processPaymentNotify(JSONObject data);

}
