package one.motion.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import one.motion.mall.dto.*;
import one.motion.mall.mapper.MallOrderMapper;
import one.motion.mall.mapper.MallProductMapper;
import one.motion.mall.model.MallOrder;
import one.motion.mall.model.MallProduct;
import one.motion.mall.service.IOrderService;
import one.motion.mall.service.IPaymentService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
@TestExecutionListeners(listeners = MockitoTestExecutionListener.class)
public class OrderServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private MallProductMapper productMapper;

    @Autowired
    private MallOrderMapper orderMapper;

    @MockBean
    private IPaymentService mockPaymentService;

    @BeforeClass
    public void beforeClass() {
        MallProduct product = new MallProduct();
        product.setProductId("12345");
        product.setCategoryCode("category_001");
        product.setCurrency(Currency.CNY.toString());
        product.setDiscount(0.9f);
        product.setEnabled((byte) EnabledStatus.ENABLED.ordinal());
        product.setName("test");
        product.setPrice(9d);
        product.setType(ProductType.S_DIRECT_CHARGE.getCode().byteValue());
        product.setUuid(UUID.randomUUID().toString());
        productMapper.insertSelective(product);
    }

    @AfterClass
    public void afterClass() {
        MallProduct product = new MallProduct();
        product.setProductId("12345");
        productMapper.delete(product);
    }

    @Test
    public void testMtnBuySuccess() {
        when(mockPaymentService.getMtnValue(Mockito.any(), Mockito.anyString())).thenReturn(BigDecimal.valueOf(12.34));
        String orderId = orderService.checkout(380L, "12345", 1, PayType.MTN);
        Assert.assertNotNull(orderId);
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getUserId(), Long.valueOf(380L));
        Assert.assertEquals(order.getMtnAmount(), 12.34);
        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setOrderId(orderId);
        paymentResult.setAmount(BigDecimal.valueOf(12.34));
        paymentResult.setCurrency(Currency.MTN);
        paymentResult.setResultCode("200");
        paymentResult.setResultMessage("success");
        paymentResult.setTime(new Date());
        paymentResult.setUserId(380L);
        paymentResult.setStatus(PaymentStatus.PAID);
        when(mockPaymentService.mtnPay(Mockito.anyString())).thenReturn(paymentResult);
        order = orderService.submit(orderId);
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getPayStatus(), (Byte) PaymentStatus.PAID.getCode().byteValue());
        Assert.assertEquals(order.getExchangeStatus(), (Byte) ExchangeStatus.EXCHANGED.getCode().byteValue());
    }

    @Test
    public void testMtnBuyFail() {
        when(mockPaymentService.getMtnValue(Mockito.any(), Mockito.anyString())).thenReturn(BigDecimal.valueOf(12.34));
        String orderId = orderService.checkout(380L, "12345", 1, PayType.MTN);
        Assert.assertNotNull(orderId);
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getUserId(), Long.valueOf(380L));
        Assert.assertEquals(order.getMtnAmount(), 12.34);
        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setOrderId(orderId);
        paymentResult.setAmount(BigDecimal.valueOf(12.34));
        paymentResult.setCurrency(Currency.MTN);
        paymentResult.setResultCode("400");
        paymentResult.setResultMessage("fail");
        paymentResult.setTime(new Date());
        paymentResult.setUserId(380L);
        paymentResult.setStatus(PaymentStatus.PAY_FAIL);
        when(mockPaymentService.mtnPay(Mockito.anyString())).thenReturn(paymentResult);
        order = orderService.submit(orderId);
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getPayStatus(), (Byte) PaymentStatus.PAY_FAIL.getCode().byteValue());
        Assert.assertEquals(order.getExchangeStatus(), (Byte) ExchangeStatus.NOT_EXCHANGED.getCode().byteValue());
    }

    @Test
    public void testCashBuySuccess() {
        when(mockPaymentService.getMtnValue(Mockito.any(), Mockito.anyString())).thenReturn(BigDecimal.valueOf(12.34));
        String orderId = orderService.checkout(380L, "12345", 1, PayType.CASH);
        Assert.assertNotNull(orderId);
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getUserId(), Long.valueOf(380L));
        Assert.assertEquals(order.getMtnAmount(), 12.34);
        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setOrderId(orderId);
        paymentResult.setAmount(BigDecimal.valueOf(order.getAmount()));
        paymentResult.setCurrency(Currency.CNY);
        paymentResult.setResultCode("200");
        paymentResult.setResultMessage("success");
        paymentResult.setTime(new Date());
        paymentResult.setUserId(380L);
        paymentResult.setStatus(PaymentStatus.IN_PAY);
        when(mockPaymentService.cashPay(orderId)).thenReturn(paymentResult);
        order = orderService.submit(orderId);
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getPayStatus(), (Byte) PaymentStatus.IN_PAY.getCode().byteValue());
        Assert.assertEquals(order.getExchangeStatus(), (Byte) ExchangeStatus.NOT_EXCHANGED.getCode().byteValue());
        PaymentResult paymentResult2 = new PaymentResult();
        paymentResult2.setOrderId(orderId);
        paymentResult2.setAmount(BigDecimal.valueOf(order.getAmount()));
        paymentResult2.setCurrency(Currency.CNY);
        paymentResult2.setResultCode("200");
        paymentResult2.setResultMessage("success");
        paymentResult2.setTime(new Date());
        paymentResult2.setUserId(380L);
        paymentResult2.setStatus(PaymentStatus.PAID);
        when(mockPaymentService.processPaymentNotify(Mockito.any())).thenReturn(paymentResult2);
        order = orderService.paymentNotify(new JSONObject());
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getPayStatus(), (Byte) PaymentStatus.PAID.getCode().byteValue());
        Assert.assertEquals(order.getExchangeStatus(), (Byte) ExchangeStatus.EXCHANGED.getCode().byteValue());
    }
}