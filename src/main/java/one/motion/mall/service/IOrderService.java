package one.motion.mall.service;

import com.github.pagehelper.PageInfo;
import com.alibaba.fastjson.JSONObject;
import one.motion.mall.dto.ExchangeResult;
import one.motion.mall.dto.PayChannel;
import one.motion.mall.dto.PayType;
import one.motion.mall.model.MallOrder;

public interface IOrderService {

    String checkout(Long userId, String ipAddress, String attach, String productId, Integer amount, PayType payType);

    JSONObject submit(String orderId, boolean isMobile, PayChannel channel);

    MallOrder refund(String orderId);

    MallOrder cancel(String orderId);

    MallOrder paymentNotify(String data, PayType payType);

    MallOrder exchangeNotify(ExchangeResult result);

    PageInfo<MallOrder> selectPage(MallOrder order,Integer offset, Integer limit);
}
