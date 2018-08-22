package one.motion.mall.dto;

public enum ProductType {
    S_DIRECT_CHARGE(1), S_CARD(2), M_CUSTOM(3);

    private Integer code;

    private ProductType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
}
