package one.motion.mall.dto;

public enum PayChannel {
    MOTION(0), WECHAT(1), ALIPAY(2);

    private Integer code;

    private PayChannel(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }

    public static PayChannel valueOf(int code) {
        switch (code) {
            case 0:
                return MOTION;
            case 1:
                return WECHAT;
            case 2:
                return ALIPAY;
            default:
                return null;
        }
    }
}
