package one.motion.mall.service;

import com.alibaba.fastjson.JSONObject;

public interface IExchangeService {
    JSONObject exchange(String orderId);
}
