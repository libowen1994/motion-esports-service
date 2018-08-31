package one.motion.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import one.motion.mall.dto.*;
import one.motion.mall.mapper.MallOrderMapper;
import one.motion.mall.mapper.MallProductMapper;
import one.motion.mall.model.MallOrder;
import one.motion.mall.model.MallProduct;
import one.motion.mall.service.IOrderService;
import one.motion.mall.service.IPaymentService;
import one.motion.mall.service.IWalletService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class SHBPaymentServiceImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private MallProductMapper productMapper;

    @Autowired
    private MallOrderMapper orderMapper;

    @MockBean
    private IWalletService mockWalletService;

    @Autowired
    @Qualifier("shbPaymentService")
    private IPaymentService shbPaymentService;

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
    public void testToPay() {
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
        JSONObject json = orderService.submit(orderId, PayChannel.WECHAT);
        logger.info(json);
        Assert.assertNotNull(json);
        PaymentResult paymentResult = shbPaymentService.queryPaymentStatus(orderId);
        Assert.assertNotNull(paymentResult);
    }

    @Test
    public void testProcessPaymentNotify() {
    }

    @Test
    public void testQueryPaymentStatus() {
    }
}