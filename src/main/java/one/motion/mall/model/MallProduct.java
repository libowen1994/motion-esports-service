package one.motion.mall.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mall_product")
public class MallProduct {
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
     * 图标
     */
    @Column(name = "icon_url")
    private String iconUrl;

    /**
     * 1,树鱼直充；2，树鱼卡密；3，自定义
     */
    private Byte type;

    /**
     * 名字
     */
    private String name;

    /**
     * 短描述
     */
    @Column(name = "short_desc")
    private String shortDesc;

    /**
     * 分类代码
     */
    @Column(name = "category_code")
    private String categoryCode;

    /**
     * 语言代码（zh_CN,zh_TW...）
     */
    @Column(name = "lang_code")
    private String langCode;

    /**
     * 商品代码
     */
    @Column(name = "product_id")
    private String productId;

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
     * 发票点数
     */
    @Column(name = "invoice_rate")
    private Float invoiceRate;

    /**
     * 销量
     */
    @Column(name = "sales_volume")
    private Integer salesVolume;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 启用/禁用
     */
    private Byte enabled;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * 商品描述
     */
    private String description;

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
     * 获取图标
     *
     * @return icon_url - 图标
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * 设置图标
     *
     * @param iconUrl 图标
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl == null ? null : iconUrl.trim();
    }

    /**
     * 获取1,树鱼直充；2，树鱼卡密；3，自定义
     *
     * @return type - 1,树鱼直充；2，树鱼卡密；3，自定义
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置1,树鱼直充；2，树鱼卡密；3，自定义
     *
     * @param type 1,树鱼直充；2，树鱼卡密；3，自定义
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
     * 获取短描述
     *
     * @return short_desc - 短描述
     */
    public String getShortDesc() {
        return shortDesc;
    }

    /**
     * 设置短描述
     *
     * @param shortDesc 短描述
     */
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc == null ? null : shortDesc.trim();
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
     * 获取语言代码（zh_CN,zh_TW...）
     *
     * @return lang_code - 语言代码（zh_CN,zh_TW...）
     */
    public String getLangCode() {
        return langCode;
    }

    /**
     * 设置语言代码（zh_CN,zh_TW...）
     *
     * @param langCode 语言代码（zh_CN,zh_TW...）
     */
    public void setLangCode(String langCode) {
        this.langCode = langCode == null ? null : langCode.trim();
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
     * 获取发票点数
     *
     * @return invoice_rate - 发票点数
     */
    public Float getInvoiceRate() {
        return invoiceRate;
    }

    /**
     * 设置发票点数
     *
     * @param invoiceRate 发票点数
     */
    public void setInvoiceRate(Float invoiceRate) {
        this.invoiceRate = invoiceRate;
    }

    /**
     * 获取销量
     *
     * @return sales_volume - 销量
     */
    public Integer getSalesVolume() {
        return salesVolume;
    }

    /**
     * 设置销量
     *
     * @param salesVolume 销量
     */
    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Long getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * 获取启用/禁用
     *
     * @return enabled - 启用/禁用
     */
    public Byte getEnabled() {
        return enabled;
    }

    /**
     * 设置启用/禁用
     *
     * @param enabled 启用/禁用
     */
    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
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
     * 获取商品描述
     *
     * @return description - 商品描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置商品描述
     *
     * @param description 商品描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}