package one.motion.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import one.motion.mall.dto.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private MallOrderMapper orderMapper;
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

    private MallOrder paymentFinished(MallOrder order, PaymentResult paymentResult) {
        PaymentStatus orderStatus = PaymentStatus.valueOf(order.getPayStatus());
        PaymentStatus status = paymentResult.getStatus();
        if (orderStatus == status) {
            return order;
        }
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
        order.setPaymentOrderId(paymentResult.getPaymentOrderId());
        order.setPayResult(StringUtils.defaultIfBlank(paymentResult.getResultCode(), "") + "_" + StringUtils.defaultIfBlank(paymentResult.getResultMessage(), ""));
        order.setCreatedAt(null);
        order.setUpdatedAt(null);
        orderMapper.updateByPrimaryKeySelective(order);
        return order;
    }

    private MallOrder exchangeFinished(MallOrder order, ExchangeResult exchangeResult) {
        ExchangeStatus orderExchangeStatus = ExchangeStatus.valueOf(order.getExchangeStatus());
        ExchangeStatus status = exchangeResult.getStatus();
        if (orderExchangeStatus == status) {
            return order;
        }
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
        order.setExchangeOrderId(exchangeResult.getExchangeOrderId());
        order.setExchangeResult(StringUtils.defaultIfBlank(exchangeResult.getResultCode(), "") + "_" + StringUtils.defaultIfBlank(exchangeResult.getResultMessage(), ""));
        order.setCreatedAt(null);
        order.setUpdatedAt(null);
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
            PaymentResult toPay = new PaymentResult();
            toPay.setOrderId(orderId);
            toPay.setAmount(BigDecimal.valueOf(order.getMtnAmount()));
            toPay.setCurrency(Currency.MTN);
            toPay.setResultCode("200");
            toPay.setResultMessage("success");
            toPay.setTime(new Date());
            toPay.setUserId(order.getUserId());
            toPay.setStatus(PaymentStatus.IN_PAY);
            order = paymentFinished(order, toPay);
            PaymentResult paymentResult = paymentService.mtnPay(order);
            order = paymentFinished(order, paymentResult);
            order = toExchange(order);

        } else {
            PaymentResult paymentResult = paymentService.cashPay(order, PayType.SHB);
            order = paymentFinished(order, paymentResult);
        }
        return order;
    }

    private MallOrder toExchange(MallOrder order) {
        if (order.getPayStatus().equals(PaymentStatus.PAID.getCode().byteValue())
                && (order.getExchangeStatus().equals(ExchangeStatus.NOT_EXCHANGED.getCode().byteValue())
                || order.getExchangeStatus().equals(ExchangeStatus.EXCHANGE_FAIL.getCode().byteValue()))) {
            ExchangeResult toExchange = new ExchangeResult();
            toExchange.setTime(new Date());
            toExchange.setStatus(ExchangeStatus.EXCHANGING);
            toExchange.setResultCode("200");
            toExchange.setResultMessage("success");
            toExchange.setOrderId(order.getOrderId());
            order = exchangeFinished(order, toExchange);
            ExchangeResult exchangeResult = exchangeService.exchange(order.getOrderId());
            order = exchangeFinished(order, exchangeResult);
        }
        return order;
    }

    @Override
    public MallOrder refund(String orderId) {
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        if (order == null) {
            throw new RuntimeException("unknown_order_error");
        }
        PaymentResult toPay = new PaymentResult();
        toPay.setOrderId(orderId);
        toPay.setAmount(BigDecimal.valueOf(order.getMtnAmount()));
        toPay.setCurrency(Currency.valueOf(order.getCurrency()));
        toPay.setResultCode("200");
        toPay.setResultMessage("refunded");
        toPay.setTime(new Date());
        toPay.setUserId(order.getUserId());
        toPay.setStatus(PaymentStatus.REFUND);
        order = paymentFinished(order, toPay);
        return order;
    }

    @Override
    public MallOrder cancel(String orderId) {
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        if (order == null) {
            throw new RuntimeException("unknown_order_error");
        }
        PaymentResult toPay = new PaymentResult();
        toPay.setOrderId(orderId);
        toPay.setAmount(BigDecimal.valueOf(order.getMtnAmount()));
        toPay.setCurrency(Currency.valueOf(order.getCurrency()));
        toPay.setResultCode("200");
        toPay.setResultMessage("canceled");
        toPay.setTime(new Date());
        toPay.setUserId(order.getUserId());
        toPay.setStatus(PaymentStatus.CANCELED);
        order = paymentFinished(order, toPay);
        return order;
    }

    @Override
    public MallOrder paymentNotify(String data, PayType payType) {
        if (data == null) {
            throw new RuntimeException("unknown_data_error");
        }
        PaymentResult paymentResult = paymentService.processPaymentNotify(data, payType);
        String orderId = paymentResult.getOrderId();
        if (StringUtils.isBlank(orderId)) {
            throw new RuntimeException("unknown_orderId_error");
        }
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        if (order == null) {
            throw new RuntimeException("unknown_order_error");
        }
        order = paymentFinished(order, paymentResult);
        order = toExchange(order);
        return order;
    }

    @Override
    public MallOrder exchangeNotify(String data) {
        if (data == null) {
            throw new RuntimeException("unknown_data_error");
        }
        ExchangeResult exchangeResult = exchangeService.processExchangeNotify(data);
        String orderId = exchangeResult.getOrderId();
        if (StringUtils.isBlank(orderId)) {
            throw new RuntimeException("unknown_orderId_error");
        }
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        if (order == null) {
            throw new RuntimeException("unknown_order_error");
        }
        order = exchangeFinished(order, exchangeResult);
        return order;
    }

    @Override
    public PageInfo<MallOrder> selectPage(MallOrder order,Integer offset, Integer limit) {
        int pageNum = offset / limit + 1;
        PageHelper.startPage(pageNum,limit);
        Example ex = new Example(MallOrder.class);
        List<MallOrder> list = orderMapper.select(order);
        return new PageInfo<>(list);
    }
}
