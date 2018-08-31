package one.motion.mall.dto;

public enum PayType {
    MTN(1), IPS(2), SHB(3);

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

    public static PayType valueOf(int code) {
        switch (code) {
            case 1:
                return MTN;
            case 2:
                return IPS;
            case 3:
                return SHB;
            default:
                return null;
        }
    }
}
