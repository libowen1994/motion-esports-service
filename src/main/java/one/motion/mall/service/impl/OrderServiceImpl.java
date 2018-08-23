package one.motion.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import one.motion.mall.dto.Currency;
import one.motion.mall.dto.ExchangeStatus;
import one.motion.mall.dto.PayType;
import one.motion.mall.dto.PaymentStatus;
import one.motion.mall.mapper.MallOrderMapper;
import one.motion.mall.mapper.MallProductCategoryMapper;
import one.motion.mall.mapper.MallProductMapper;
import one.motion.mall.model.MallOrder;
import one.motion.mall.model.MallProduct;
import one.motion.mall.service.IExchangeService;
import one.motion.mall.service.IOrderService;
import one.motion.mall.service.IPaymentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {
    private final MallOrderMapper orderMapper;
    private final MallProductMapper productMapper;
    private final MallProductCategoryMapper productCategoryMapper;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IPaymentService paymentService;
    private final IExchangeService exchangeService;

    public OrderServiceImpl(MallOrderMapper orderMapper, MallProductMapper productMapper, MallProductCategoryMapper productCategoryMapper, RestTemplate restTemplate, IPaymentService paymentService, IExchangeService exchangeService) {
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
        this.productCategoryMapper = productCategoryMapper;
        this.paymentService = paymentService;
        this.exchangeService = exchangeService;
    }

    private String getOrderNo(Long userId, Date date) {
        Example example = new Example(MallOrder.class);
        String dateStr = DateFormatUtils.format(date, "yyyy-MM-dd");
        example.and().andBetween("createdAt", dateStr + " 00:00:00", dateStr + " 23:59:59").andEqualTo("userId", userId);
        int count = orderMapper.selectCountByExample(example);
        DecimalFormat df = new DecimalFormat("0000000");
        DecimalFormat df2 = new DecimalFormat("0000");
        return df.format(userId)
                + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + df2.format(count);
    }

    @Override
    public String checkout(Long userId, String productId, Integer amount, PayType payType) {
        if (userId == null) {
            throw new RuntimeException("unknown_userId_error");
        }
        if (productId == null) {
            throw new RuntimeException("unknown_productId_error");
        }
        if (amount == null) {
            throw new RuntimeException("unknown_amount_error");
        }
        if (payType == null) {
            throw new RuntimeException("unknown_payType_error");
        }
        MallProduct product = new MallProduct();
        product.setProductId(productId);
        product = productMapper.selectOne(product);
        if (product == null) {
            throw new RuntimeException("unknown_product_error");
        }
        String orderId = getOrderNo(userId, new Date());
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order.setAmount(amount);
        order.setPrice(product.getPrice());
        order.setCategoryCode(product.getCategoryCode());
        order.setCurrency(product.getCurrency());
        order.setDiscount(product.getDiscount());
        order.setProductId(product.getProductId());
        order.setName(product.getName());
        order.setPayType(payType.getCode().byteValue());
        BigDecimal total = BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(product.getPrice()));
        if (product.getDiscount() != null) {
            total = total.multiply(BigDecimal.valueOf(product.getDiscount()));
        }
        BigDecimal mtn = paymentService.getMtnValue(total, product.getCurrency());
        order.setMtnAmount(mtn.doubleValue());
        order.setType(product.getType());
        order.setUserId(userId);
        order.setUuid(UUID.randomUUID().toString());
        order.setPayStatus(PaymentStatus.UNPAID.getCode().byteValue());
        order.setExchangeStatus(ExchangeStatus.NOT_EXCHANGED.getCode().byteValue());
        orderMapper.insertSelective(order);
        logger.info("Order [{}] is created", orderId);
        return orderId;
    }

    private MallOrder paymentFinished(MallOrder order, PaymentStatus status, JSONObject data) {
        PaymentStatus orderStatus = PaymentStatus.valueOf(order.getPayStatus());
        if (orderStatus == null) {
            throw new RuntimeException("unknown_order_status_error");
        }
        if (orderStatus != PaymentStatus.UNPAID && status == PaymentStatus.IN_PAY) {
            throw new RuntimeException("order_payment_status_error");
        }
        if ((orderStatus != PaymentStatus.IN_PAY && orderStatus != PaymentStatus.PAY_FAIL) && status == PaymentStatus.PAID) {
            throw new RuntimeException("order_payment_status_error");
        }
        if (orderStatus != PaymentStatus.IN_PAY && status == PaymentStatus.PAY_FAIL) {
            throw new RuntimeException("order_payment_status_error");
        }
        if (orderStatus != PaymentStatus.PAID && status == PaymentStatus.REFUND) {
            throw new RuntimeException("order_payment_status_error");
        }
        if (orderStatus != PaymentStatus.UNPAID && status == PaymentStatus.CANCELED) {
            throw new RuntimeException("order_payment_status_error");
        }
        order.setPayStatus(status.getCode().byteValue());
        if (data != null) {
            order.setPayResult(data.toJSONString());
        }
        orderMapper.updateByPrimaryKeySelective(order);
        return order;
    }

    private MallOrder exchangeFinished(MallOrder order, ExchangeStatus status, JSONObject data) {
        ExchangeStatus orderExchangeStatus = ExchangeStatus.valueOf(order.getExchangeStatus());
        if (orderExchangeStatus == null) {
            throw new RuntimeException("unknown_order_exchange_status_error");
        }
        if (orderExchangeStatus != ExchangeStatus.NOT_EXCHANGED && status == ExchangeStatus.EXCHANGING) {
            throw new RuntimeException("order_exchange_status_error");
        }
        if (orderExchangeStatus != ExchangeStatus.EXCHANGING && status == ExchangeStatus.EXCHANGE_FAIL) {
            throw new RuntimeException("order_exchange_status_error");
        }
        if (orderExchangeStatus != ExchangeStatus.EXCHANGING && status == ExchangeStatus.EXCHANGED) {
            throw new RuntimeException("order_exchange_status_error");
        }
        order.setExchangeStatus(status.getCode().byteValue());
        if (data != null) {
            order.setExchangeResult(data.toJSONString());
        }
        orderMapper.updateByPrimaryKeySelective(order);
        return order;
    }

    @Override
    public MallOrder submit(String orderId) {
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        if (order == null) {
            throw new RuntimeException("unknown_order_error");
        }
        if (PayType.valueOf(order.getPayType()) == PayType.MTN) {
            paymentFinished(order, PaymentStatus.IN_PAY, null);
            JSONObject json = paymentService.mtnPay(order.getOrderId(), BigDecimal.valueOf(order.getMtnAmount()), Currency.MTN);
            if (json != null && StringUtils.equals("200", json.getString("code"))) {
                order = paymentFinished(order, PaymentStatus.PAID, json);
            } else {
                order = paymentFinished(order, PaymentStatus.PAY_FAIL, json);
            }
            JSONObject exchangeResult = exchangeService.exchange(orderId);
            exchangeFinished(order, ExchangeStatus.EXCHANGING, null);
            if (exchangeResult != null && StringUtils.equals("200", exchangeResult.getString("code"))) {
                order = exchangeFinished(order, ExchangeStatus.EXCHANGED, exchangeResult);
            } else {
                order = exchangeFinished(order, ExchangeStatus.EXCHANGE_FAIL, exchangeResult);
            }

        }
        if (PayType.valueOf(order.getPayType()) == PayType.CASH) {
            JSONObject json = paymentService.ipsPay(order.getOrderId(), BigDecimal.valueOf(order.getMtnAmount()), Currency.MTN);
            if (json != null && StringUtils.equals("200", json.getString("code"))) {
                paymentFinished(order, PaymentStatus.IN_PAY, json);
            } else {
                order = paymentFinished(order, PaymentStatus.PAY_FAIL, json);
            }
        }
        return order;
    }

    @Override
    public MallOrder refund(String orderId) {
        return null;
    }

    @Override
    public MallOrder cancel(String orderId) {
        return null;
    }
}
