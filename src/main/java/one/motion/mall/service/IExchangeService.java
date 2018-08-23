package one.motion.mall.service;

import com.alibaba.fastjson.JSONObject;
import one.motion.mall.dto.ExchangeResult;

public interface IExchangeService {
    ExchangeResult exchange(String orderId);

    ExchangeResult processExchangeNotify(JSONObject data);
}
