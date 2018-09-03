package one.motion.mall.service.impl;

import com.alibaba.fastjson.JSON;
import one.motion.mall.config.KafkaConfig;
import one.motion.mall.dto.ExchangeCommand;
import one.motion.mall.dto.ExchangeResult;
import one.motion.mall.dto.ExchangeStatus;
import one.motion.mall.dto.ProductType;
import one.motion.mall.mapper.MallOrderMapper;
import one.motion.mall.model.MallOrder;
import one.motion.mall.service.IExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExchangeServiceImpl implements IExchangeService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MallOrderMapper orderMapper;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ExchangeServiceImpl(KafkaTemplate<String, String> kafkaTemplate, MallOrderMapper orderMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderMapper = orderMapper;
    }


    @Override
    public ExchangeResult exchange(String orderId) {
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        if (order == null) {
            throw new RuntimeException("unknown_order_error");
        }
        ExchangeCommand command = new ExchangeCommand();
        command.setAmount(order.getAmount());
        command.setProductId(order.getProductId());
        command.setOrderId(order.getOrderId());
        command.setAttach(order.getAttach());
        command.setRemark(order.getRemark());
        command.setUserId(order.getUserId());
        command.setProductType(ProductType.valueOf(order.getType()));
        command.setIpAddress(order.getIpAddress());
        ExchangeResult exchangeResult = new ExchangeResult();
        try {
            kafkaTemplate.send(KafkaConfig.MALL_EXCHANGE_TOPIC, JSON.toJSONString(command));
            exchangeResult.setResultCode("200");
            exchangeResult.setResultMessage("success");
            exchangeResult.setStatus(ExchangeStatus.EXCHANGING);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            exchangeResult.setResultCode("500");
            exchangeResult.setResultMessage("fail:" + e.getMessage());
            exchangeResult.setStatus(ExchangeStatus.EXCHANGE_FAIL);
        }
        exchangeResult.setOrderId(orderId);
        exchangeResult.setTime(new Date());
        return exchangeResult;
    }
}
