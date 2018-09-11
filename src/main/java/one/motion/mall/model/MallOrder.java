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
    @Column(name = "product_name")
    private String productName;

    /**
     * 分类代码
     */
    @Column(name = "category_code")
    private String categoryCode;

    /**
     * 商品语言代码
     */
    @Column(name = "lang_code")
    private String langCode;

    /**
     * ip
     */
    @Column(name = "ip_address")
    private String ipAddress;

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
     * 总价
     */
    @Column(name = "total_amount")
    private Double totalAmount;

    /**
     * 手续费
     */
    private Double fee;

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
     * 付款方式(1mtn付款, 2：IPS, 3：SHB)
     */
    @Column(name = "pay_type")
    private Byte payType;

    /**
     * 支付系统订单号
     */
    @Column(name = "payment_order_id")
    private String paymentOrderId;

    /**
     * 订单付款状态：1未付款、2已付款、3已退款, 4付款失败，5已取消
     */
    @Column(name = "pay_status")
    private Byte payStatus;

    /**
     * 支付结果代码
     */
    @Column(name = "pay_result_code")
    private String payResultCode;

    /**
     * 兑换订单号
     */
    @Column(name = "exchange_order_id")
    private String exchangeOrderId;

    /**
     * 订单状态：1未兑换，2兑换进行中，2已兌換成功，3兑换失败
     */
    @Column(name = "exchange_status")
    private Byte exchangeStatus;

    /**
     * 兑换结果代码
     */
    @Column(name = "exchange_result_code")
    private String exchangeResultCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 附加字段
     */
    private String attach;

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
     * @return product_name - 名字
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置名字
     *
     * @param productName 名字
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
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
     * 获取商品语言代码
     *
     * @return lang_code - 商品语言代码
     */
    public String getLangCode() {
        return langCode;
    }

    /**
     * 设置商品语言代码
     *
     * @param langCode 商品语言代码
     */
    public void setLangCode(String langCode) {
        this.langCode = langCode == null ? null : langCode.trim();
    }

    /**
     * 获取ip
     *
     * @return ip_address - ip
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 设置ip
     *
     * @param ipAddress ip
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
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
     * 获取总价
     *
     * @return total_amount - 总价
     */
    public Double getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置总价
     *
     * @param totalAmount 总价
     */
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取手续费
     *
     * @return fee - 手续费
     */
    public Double getFee() {
        return fee;
    }

    /**
     * 设置手续费
     *
     * @param fee 手续费
     */
    public void setFee(Double fee) {
        this.fee = fee;
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
     * 获取付款方式(1mtn付款, 2：IPS, 3：SHB)
     *
     * @return pay_type - 付款方式(1mtn付款, 2：IPS, 3：SHB)
     */
    public Byte getPayType() {
        return payType;
    }

    /**
     * 设置付款方式(1mtn付款, 2：IPS, 3：SHB)
     *
     * @param payType 付款方式(1mtn付款, 2：IPS, 3：SHB)
     */
    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    /**
     * 获取支付系统订单号
     *
     * @return payment_order_id - 支付系统订单号
     */
    public String getPaymentOrderId() {
        return paymentOrderId;
    }

    /**
     * 设置支付系统订单号
     *
     * @param paymentOrderId 支付系统订单号
     */
    public void setPaymentOrderId(String paymentOrderId) {
        this.paymentOrderId = paymentOrderId == null ? null : paymentOrderId.trim();
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
     * 获取支付结果代码
     *
     * @return pay_result_code - 支付结果代码
     */
    public String getPayResultCode() {
        return payResultCode;
    }

    /**
     * 设置支付结果代码
     *
     * @param payResultCode 支付结果代码
     */
    public void setPayResultCode(String payResultCode) {
        this.payResultCode = payResultCode == null ? null : payResultCode.trim();
    }

    /**
     * 获取兑换订单号
     *
     * @return exchange_order_id - 兑换订单号
     */
    public String getExchangeOrderId() {
        return exchangeOrderId;
    }

    /**
     * 设置兑换订单号
     *
     * @param exchangeOrderId 兑换订单号
     */
    public void setExchangeOrderId(String exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId == null ? null : exchangeOrderId.trim();
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
     * 获取兑换结果代码
     *
     * @return exchange_result_code - 兑换结果代码
     */
    public String getExchangeResultCode() {
        return exchangeResultCode;
    }

    /**
     * 设置兑换结果代码
     *
     * @param exchangeResultCode 兑换结果代码
     */
    public void setExchangeResultCode(String exchangeResultCode) {
        this.exchangeResultCode = exchangeResultCode == null ? null : exchangeResultCode.trim();
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取附加字段
     *
     * @return attach - 附加字段
     */
    public String getAttach() {
        return attach;
    }

    /**
     * 设置附加字段
     *
     * @param attach 附加字段
     */
    public void setAttach(String attach) {
        this.attach = attach == null ? null : attach.trim();
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