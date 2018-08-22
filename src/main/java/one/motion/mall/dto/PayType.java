package one.motion.mall.dto;

public enum PayType {
    CASH(1), MTN(2);

    private Integer code;

    private PayType(Integer code) {
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
