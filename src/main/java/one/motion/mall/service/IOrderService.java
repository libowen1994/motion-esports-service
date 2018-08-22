package one.motion.mall.service;

import one.motion.mall.dto.PayType;
import one.motion.mall.model.MallOrder;

public interface IOrderService {

    String newOrder(Long userId, String productId, Integer amount, PayType payType);

    MallOrder paymentFinished(String orderId, boolean status, String code, String message);

    MallOrder exchangeFinished(String orderId, boolean status, String code, String message);

    MallOrder refund(String orderId);

    MallOrder cancel(String orderId);

}
