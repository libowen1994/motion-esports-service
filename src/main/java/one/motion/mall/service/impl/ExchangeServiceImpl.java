package one.motion.mall.service.impl;

import one.motion.mall.dto.ExchangeResult;
import one.motion.mall.dto.ExchangeStatus;
import one.motion.mall.service.IExchangeService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExchangeServiceImpl implements IExchangeService {
    @Override
    public ExchangeResult exchange(String orderId) {
        ExchangeResult exchangeResult = new ExchangeResult();
        exchangeResult.setOrderId(orderId);
        exchangeResult.setResultCode("200");
        exchangeResult.setResultMessage("success");
        exchangeResult.setStatus(ExchangeStatus.EXCHANGED);
        exchangeResult.setTime(new Date());
        return exchangeResult;
    }

    @Override
    public ExchangeResult processExchangeNotify(String data) {
        ExchangeResult exchangeResult = new ExchangeResult();
        exchangeResult.setOrderId(data);
        exchangeResult.setResultCode("200");
        exchangeResult.setResultMessage("success");
        exchangeResult.setStatus(ExchangeStatus.EXCHANGED);
        exchangeResult.setTime(new Date());
        return exchangeResult;
    }
}
