package one.motion.mall.dto;

public enum PaymentStatus {
    //1未付款、2已付款、3已退款, 4付款失败，5已取消, 6正在支付
    UNPAID(1), PAID(2), REFUND(3), PAY_FAIL(4), CANCELED(5), IN_PAY(6);

    private Integer code;

    private PaymentStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(this.code);
    }

    public static PaymentStatus valueOf(int code) {
        switch (code) {
            case 1:
                return UNPAID;
            case 2:
                return PAID;
            case 3:
                return REFUND;
            case 4:
                return PAY_FAIL;
            case 5:
                return CANCELED;
            case 6:
                return IN_PAY;
            default:
                return null;
        }
    }
}
