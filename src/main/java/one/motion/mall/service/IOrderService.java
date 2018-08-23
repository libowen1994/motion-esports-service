package one.motion.mall.service;

import com.alibaba.fastjson.JSONObject;
import one.motion.mall.dto.PayType;
import one.motion.mall.model.MallOrder;

public interface IOrderService {

    String checkout(Long userId, String productId, Integer amount, PayType payType);

    MallOrder submit(String orderId);

    MallOrder refund(String orderId);

    MallOrder cancel(String orderId);

    MallOrder paymentNotify(JSONObject data);

    MallOrder exchangeNotify(JSONObject data);

}
