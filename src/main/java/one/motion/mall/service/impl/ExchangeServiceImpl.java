package one.motion.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import one.motion.mall.service.IExchangeService;
import org.springframework.stereotype.Service;

@Service
public class ExchangeServiceImpl implements IExchangeService {
    @Override
    public JSONObject exchange(String orderId) {
        JSONObject result = new JSONObject();
        result.put("code", "200");
        return result;
    }
}
