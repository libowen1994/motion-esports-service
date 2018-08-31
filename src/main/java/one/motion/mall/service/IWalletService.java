package one.motion.mall.service;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

public interface IWalletService {
    BigDecimal getMtnValue(BigDecimal amount, String currency);

    JSONObject expendMTN(BigDecimal amount, String orderId,Long userId);
}
