package one.motion.mall.service;

import one.motion.mall.dto.PayChannel;
import one.motion.mall.dto.PaymentResult;

public interface IPaymentService {

    PaymentResult toPay(String orderId, boolean isMobile, PayChannel channel);

    PaymentResult processPaymentNotify(String data);


    PaymentResult queryPaymentStatus(String orderId);

}
