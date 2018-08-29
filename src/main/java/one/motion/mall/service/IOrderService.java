package one.motion.mall.service;

import com.github.pagehelper.PageInfo;
import one.motion.mall.dto.PayType;
import one.motion.mall.model.MallOrder;

public interface IOrderService {

    String checkout(Long userId, String productId, Integer amount, PayType payType);

    MallOrder submit(String orderId);

    MallOrder refund(String orderId);

    MallOrder cancel(String orderId);

    MallOrder paymentNotify(String data, PayType payType);

    MallOrder exchangeNotify(String data);

    PageInfo<MallOrder> selectPage(MallOrder order,Integer offset, Integer limit);
}
