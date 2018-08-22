package one.motion.mall.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "mall_product_category")
public class MallProductCategory {
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
     * 1,树鱼直充；2，树鱼卡密；3，自定义
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
}