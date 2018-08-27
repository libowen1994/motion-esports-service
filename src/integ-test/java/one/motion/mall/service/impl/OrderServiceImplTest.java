package one.motion.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import one.motion.mall.dto.*;
import one.motion.mall.mapper.MallOrderMapper;
import one.motion.mall.mapper.MallProductMapper;
import one.motion.mall.model.MallOrder;
import one.motion.mall.model.MallProduct;
import one.motion.mall.service.IOrderService;
import one.motion.mall.service.IWalletService;
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
    private IWalletService mockWalletService;

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
        when(mockWalletService.getMtnValue(Mockito.any(), Mockito.anyString())).thenReturn(BigDecimal.valueOf(12.34));
        String orderId = orderService.checkout(380L, "12345", 1, PayType.MTN);
        Assert.assertNotNull(orderId);
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getUserId(), Long.valueOf(380L));
        Assert.assertEquals(order.getMtnAmount(), 12.34);
        Assert.assertEquals(order.getTotalAmount(), 9d);
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("message", "success");
        when(mockWalletService.expendMTN(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(json);
        JSONObject result = orderService.submit(orderId, PayChannel.MOTION);
        order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getPayStatus(), (Byte) PaymentStatus.PAID.getCode().byteValue());
        Assert.assertEquals(order.getExchangeStatus(), (Byte) ExchangeStatus.EXCHANGED.getCode().byteValue());
    }

    @Test
    public void testMtnBuyFail() {
        when(mockWalletService.getMtnValue(Mockito.any(), Mockito.anyString())).thenReturn(BigDecimal.valueOf(12.34));
        String orderId = orderService.checkout(380L, "12345", 1, PayType.MTN);
        Assert.assertNotNull(orderId);
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getUserId(), Long.valueOf(380L));
        Assert.assertEquals(order.getMtnAmount(), 12.34);
        JSONObject json = new JSONObject();
        json.put("code", 400);
        json.put("message", "fail");
        when(mockWalletService.expendMTN(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(json);
        JSONObject result = orderService.submit(orderId, PayChannel.MOTION);
        order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getPayStatus(), (Byte) PaymentStatus.PAY_FAIL.getCode().byteValue());
        Assert.assertEquals(order.getExchangeStatus(), (Byte) ExchangeStatus.NOT_EXCHANGED.getCode().byteValue());
    }

    @Test
    public void testCashBuy() {
        when(mockWalletService.getMtnValue(Mockito.any(), Mockito.anyString())).thenReturn(BigDecimal.valueOf(12.34));
        String orderId = orderService.checkout(380L, "12345", 1, PayType.SHB);
        Assert.assertNotNull(orderId);
        MallOrder order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getUserId(), Long.valueOf(380L));
        Assert.assertEquals(order.getMtnAmount(), 12.34);
        JSONObject result = orderService.submit(orderId, PayChannel.WECHAT);
        System.out.println(result);
        order = new MallOrder();
        order.setOrderId(orderId);
        order = orderMapper.selectOne(order);
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getPayStatus(), (Byte) PaymentStatus.PAY_FAIL.getCode().byteValue());
        Assert.assertEquals(order.getExchangeStatus(), (Byte) ExchangeStatus.NOT_EXCHANGED.getCode().byteValue());
    }
}