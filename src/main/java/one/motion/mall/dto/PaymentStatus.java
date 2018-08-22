package one.motion.mall.dto;

public enum PaymentStatus {
    //1未付款、2已付款、3已退款, 4付款失败，5已取消
    UNPAID(1), PAID(2), REFUND(3), PAY_FAIL(4), CANCELED(5);

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
}
