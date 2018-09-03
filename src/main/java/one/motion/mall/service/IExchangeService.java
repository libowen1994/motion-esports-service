package one.motion.mall.service;

import one.motion.mall.dto.ExchangeResult;

public interface IExchangeService {
    ExchangeResult exchange(String orderId);
}
