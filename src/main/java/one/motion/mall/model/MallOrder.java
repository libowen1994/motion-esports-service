package one.motion.mall.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mall_order")
public class MallOrder {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 订单编号
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 商品类型：1,树鱼直充；2，树鱼卡密；3，自定义
     */
    private Byte type;

    /**
     * 名字
     */
    private String name;

    /**
     * 分类代码
     */
    @Column(name = "category_code")
    private String categoryCode;

    /**
     * 商品代码
     */
    @Column(name = "product_id")
    private String productId;

    /**
     * 商品数量
     */
    private Integer amount;

    /**
     * 单价
     */
    private Double price;

    /**
     * 币种
     */
    private String currency;

    /**
     * 折扣
     */
    private Float discount;

    /**
     * MTN价格
     */
    @Column(name = "mtn_amount")
    private Double mtnAmount;

    /**
     * 付款方式(1法币付款，2mtn付款)
     */
    @Column(name = "pay_type")
    private Byte payType;

    /**
     * 订单付款状态：1未付款、2已付款、3已退款, 4付款失败，5已取消
     */
    @Column(name = "pay_status")
    private Byte payStatus;

    /**
     * 订单状态：1未兑换，2兑换进行中，2已兌換成功，3兑换失败
     */
    @Column(name = "exchange_status")
    private Byte exchangeStatus;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 支付结果
     */
    @Column(name = "pay_result")
    private String payResult;

    /**
     * 兑换结果
     */
    @Column(name = "exchange_result")
    private String exchangeResult;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取uuid
     *
     * @return uuid - uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置uuid
     *
     * @param uuid uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * 获取订单编号
     *
     * @return order_id - 订单编号
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单编号
     *
     * @param orderId 订单编号
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取商品类型：1,树鱼直充；2，树鱼卡密；3，自定义
     *
     * @return type - 商品类型：1,树鱼直充；2，树鱼卡密；3，自定义
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置商品类型：1,树鱼直充；2，树鱼卡密；3，自定义
     *
     * @param type 商品类型：1,树鱼直充；2，树鱼卡密；3，自定义
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取名字
     *
     * @return name - 名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名字
     *
     * @param name 名字
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取分类代码
     *
     * @return category_code - 分类代码
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * 设置分类代码
     *
     * @param categoryCode 分类代码
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode == null ? null : categoryCode.trim();
    }

    /**
     * 获取商品代码
     *
     * @return product_id - 商品代码
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置商品代码
     *
     * @param productId 商品代码
     */
    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    /**
     * 获取商品数量
     *
     * @return amount - 商品数量
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * 设置商品数量
     *
     * @param amount 商品数量
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 获取单价
     *
     * @return price - 单价
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置单价
     *
     * @param price 单价
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取币种
     *
     * @return currency - 币种
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置币种
     *
     * @param currency 币种
     */
    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    /**
     * 获取折扣
     *
     * @return discount - 折扣
     */
    public Float getDiscount() {
        return discount;
    }

    /**
     * 设置折扣
     *
     * @param discount 折扣
     */
    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    /**
     * 获取MTN价格
     *
     * @return mtn_amount - MTN价格
     */
    public Double getMtnAmount() {
        return mtnAmount;
    }

    /**
     * 设置MTN价格
     *
     * @param mtnAmount MTN价格
     */
    public void setMtnAmount(Double mtnAmount) {
        this.mtnAmount = mtnAmount;
    }

    /**
     * 获取付款方式(1法币付款，2mtn付款)
     *
     * @return pay_type - 付款方式(1法币付款，2mtn付款)
     */
    public Byte getPayType() {
        return payType;
    }

    /**
     * 设置付款方式(1法币付款，2mtn付款)
     *
     * @param payType 付款方式(1法币付款，2mtn付款)
     */
    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    /**
     * 获取订单付款状态：1未付款、2已付款、3已退款, 4付款失败，5已取消
     *
     * @return pay_status - 订单付款状态：1未付款、2已付款、3已退款, 4付款失败，5已取消
     */
    public Byte getPayStatus() {
        return payStatus;
    }

    /**
     * 设置订单付款状态：1未付款、2已付款、3已退款, 4付款失败，5已取消
     *
     * @param payStatus 订单付款状态：1未付款、2已付款、3已退款, 4付款失败，5已取消
     */
    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取订单状态：1未兑换，2兑换进行中，2已兌換成功，3兑换失败
     *
     * @return exchange_status - 订单状态：1未兑换，2兑换进行中，2已兌換成功，3兑换失败
     */
    public Byte getExchangeStatus() {
        return exchangeStatus;
    }

    /**
     * 设置订单状态：1未兑换，2兑换进行中，2已兌換成功，3兑换失败
     *
     * @param exchangeStatus 订单状态：1未兑换，2兑换进行中，2已兌換成功，3兑换失败
     */
    public void setExchangeStatus(Byte exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }

    /**
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return updated_at
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 获取支付结果
     *
     * @return pay_result - 支付结果
     */
    public String getPayResult() {
        return payResult;
    }

    /**
     * 设置支付结果
     *
     * @param payResult 支付结果
     */
    public void setPayResult(String payResult) {
        this.payResult = payResult == null ? null : payResult.trim();
    }

    /**
     * 获取兑换结果
     *
     * @return exchange_result - 兑换结果
     */
    public String getExchangeResult() {
        return exchangeResult;
    }

    /**
     * 设置兑换结果
     *
     * @param exchangeResult 兑换结果
     */
    public void setExchangeResult(String exchangeResult) {
        this.exchangeResult = exchangeResult == null ? null : exchangeResult.trim();
    }
}