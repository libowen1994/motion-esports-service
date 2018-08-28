package one.motion.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import one.motion.mall.dto.*;
import one.motion.mall.mapper.MallOrderMapper;
import one.motion.mall.mapper.MallProductMapper;
import one.motion.mall.model.MallOrder;
import one.motion.mall.model.MallProduct;
import one.motion.mall.service.IExchangeService;
import one.motion.mall.service.IOrderService;
import one.motion.mall.service.IPaymentService;
import one.motion.mall.service.IWalletService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {
    private final MallOrderMapper orderMapper;
    private final MallProductMapper productMapper;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IPaymentService mtnPaymentService;
    private final IPaymentService shbPaymentService;
    private final IExchangeService exchangeService;
    private final IWalletService walletService;
    private final IPaymentService ipsPaymentService;
    private final PayType defaultCashPayType = PayType.SHB;

    public OrderServiceImpl(MallOrderMapper orderMapper,
                            MallProductMapper productMapper,
                            IExchangeService exchangeService,
                            IWalletService walletService,
                            @Qualifier("mtnPaymentService") IPaymentService mtnPaymentService,
                            @Qualifier("shbPaymentService") IPaymentService shbPaymentService,
                            @Qualifier("ipsPaymentService") IPaymentService ipsPaymentService) {
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
        this.mtnPaymentService = mtnPaymentService;
        this.shbPaymentService = shbPaymentService;
        this.exchangeService = exchangeService;
        this.walletService = walletService;
        this.ipsPaymentService = ipsPaymentService;
    }

    private String getOrderNo(Long userId) {
        String pattern = "000000000";
        DecimalFormat df = new DecimalFormat(pattern);
        String userIdStr = df.format(userId).substring(pattern.length() < 4 ? 0 : pattern.length() - 4, pattern.length());
        String timestamp = System.currentTimeMillis() + "";
        timestamp = timestamp.substring(timestamp.length() < 8 ? 0 : timestamp.length() - 8, timestamp.length());
        return DateFormatUtils.format(new Date(), "yyyyMMdd") + timestamp + userIdStr;
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
        String orderId = getOrderNo(userId);
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order.setAmount(amount);
        order.setPrice(product.getPrice());
        order.setTotalAmount(product.getPrice() * amount);
        order.setFee(0d);
        order.setCategoryCode(product.getCategoryCode());
        order.setCurrency(product.getCurrency());
        order.setDiscount(product.getDiscount());
        order.setProductId(product.getProductId());
        order.setProductName(product.getName());
        order.setPayType(payType.getCode().byteValue());
        BigDecimal total = BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(product.getPrice()));
        if (product.getDiscount() != null) {
            total = total.multiply(BigDecimal.valueOf(product.getDiscount()));
        }
        BigDecimal mtn = walletService.getMtnValue(total, product.getCurrency());
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

    private MallOrder updatePaymentStatus(MallOrder order, PaymentResult paymentResult) {
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
        if ((orderStatus != PaymentStatus.IN_PAY && orderStatus != PaymentStatus.PAY_FAIL)
                && status == PaymentStatus.PAID) {
            throw new RuntimeException("order_payment_status_error");
        }
        if ((orderStatus != PaymentStatus.IN_PAY && orderStatus != PaymentStatus.UNPAID)
                && status == PaymentStatus.PAY_FAIL) {
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
        order.setPayResult(paymentResult.getResultMessage());
        order.setCreatedAt(null);
        order.setUpdatedAt(null);
        orderMapper.updateByPrimaryKeySelective(order);
        return order;
    }

    private MallOrder updateExchangeStatus(MallOrder order, ExchangeResult exchangeResult) {
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
    public JSONObject submit(String orderId, PayChannel channel) {
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        if (order == null) {
            throw new RuntimeException("unknown_order_error");
        }
        PaymentResult paymentResult = null;
        JSONObject result = new JSONObject();
        result.put("orderId", orderId);
        if (PayType.valueOf(order.getPayType()) == PayType.MTN && channel == PayChannel.MOTION) {
            try {
                paymentResult = mtnPaymentService.toPay(orderId, PayChannel.MOTION);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                paymentResult = new PaymentResult();
                paymentResult.setOrderId(orderId);
                paymentResult.setResultCode("500");
                paymentResult.setResultMessage(e.getMessage());
                paymentResult.setTime(new Date());
                paymentResult.setUserId(order.getUserId());
                paymentResult.setStatus(PaymentStatus.PAY_FAIL);
            }
            PaymentResult toPay = new PaymentResult();
            toPay.setOrderId(orderId);
            toPay.setResultCode("200");
            toPay.setResultMessage("success");
            toPay.setTime(new Date());
            toPay.setUserId(order.getUserId());
            toPay.setStatus(PaymentStatus.IN_PAY);
            order = updatePaymentStatus(order, toPay);
            order = updatePaymentStatus(order, paymentResult);
            order = toExchange(order);
        } else {
            try {
                if (defaultCashPayType == PayType.SHB) {
                    paymentResult = shbPaymentService.toPay(orderId, channel);
                } else if (defaultCashPayType == PayType.IPS) {
                    paymentResult = ipsPaymentService.toPay(orderId, channel);
                } else {
                    paymentResult = new PaymentResult();
                    paymentResult.setOrderId(orderId);
                    paymentResult.setResultCode("500");
                    paymentResult.setResultMessage("unsupported_pay_type");
                    paymentResult.setTime(new Date());
                    paymentResult.setUserId(order.getUserId());
                    paymentResult.setStatus(PaymentStatus.PAY_FAIL);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                paymentResult = new PaymentResult();
                paymentResult.setOrderId(orderId);
                paymentResult.setResultMessage(e.getMessage());
                paymentResult.setTime(new Date());
                paymentResult.setUserId(order.getUserId());
                paymentResult.setStatus(PaymentStatus.PAY_FAIL);
            }
            updatePaymentStatus(order, paymentResult);
            result.put("data", paymentResult.getData());
        }
        result.put("paymentStatus", paymentResult.getResultCode());
        result.put("paymentMessage", paymentResult.getResultMessage());
        result.put("status", order.getPayStatus());
        return result;
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
            order = updateExchangeStatus(order, toExchange);
            ExchangeResult exchangeResult = exchangeService.exchange(order.getOrderId());
            order = updateExchangeStatus(order, exchangeResult);
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
        toPay.setTotalAmount(BigDecimal.valueOf(order.getMtnAmount()));
        toPay.setCurrency(Currency.valueOf(order.getCurrency()));
        toPay.setResultCode("200");
        toPay.setResultMessage("refunded");
        toPay.setTime(new Date());
        toPay.setUserId(order.getUserId());
        toPay.setStatus(PaymentStatus.REFUND);
        order = updatePaymentStatus(order, toPay);
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
        toPay.setTotalAmount(BigDecimal.valueOf(order.getMtnAmount()));
        toPay.setCurrency(Currency.valueOf(order.getCurrency()));
        toPay.setResultCode("200");
        toPay.setResultMessage("canceled");
        toPay.setTime(new Date());
        toPay.setUserId(order.getUserId());
        toPay.setStatus(PaymentStatus.CANCELED);
        order = updatePaymentStatus(order, toPay);
        return order;
    }

    @Override
    public MallOrder paymentNotify(String data, PayType payType) {
        if (data == null) {
            throw new RuntimeException("unknown_data_error");
        }
        PaymentResult paymentResult = null;
        if (payType == PayType.SHB) {
            paymentResult = shbPaymentService.processPaymentNotify(data);
        }
        if (payType == PayType.IPS) {
            paymentResult = ipsPaymentService.processPaymentNotify(data);
        }
        if (paymentResult == null) {
            throw new RuntimeException("payment_notify_process_error");
        }
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
        order = updatePaymentStatus(order, paymentResult);
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
        order = updateExchangeStatus(order, exchangeResult);
        return order;
    }
}
