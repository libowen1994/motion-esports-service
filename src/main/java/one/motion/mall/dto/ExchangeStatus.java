package one.motion.mall.dto;

public enum ExchangeStatus {
    //1未兑换，2兑换进行中，2已兌換成功，3兑换失败
    NOT_EXCHANGED(1), EXCHANGING(2), EXCHANGED(3), EXCHANGE_FAIL(4);
    private Integer code;

    private ExchangeStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }

    public static ExchangeStatus valueOf(int code) {
        switch (code) {
            case 1:
                return NOT_EXCHANGED;
            case 2:
                return EXCHANGING;
            case 3:
                return EXCHANGED;
            case 4:
                return EXCHANGE_FAIL;
            default:
                return null;
        }
    }
}
