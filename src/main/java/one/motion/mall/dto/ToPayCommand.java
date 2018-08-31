package one.motion.mall.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ToPayCommand {
    @NotEmpty
    private String orderId;
    @NotNull
    private PayChannel channel;
    @NotNull
    private Boolean mobile;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public PayChannel getChannel() {
        return channel;
    }

    public void setChannel(PayChannel channel) {
        this.channel = channel;
    }

    public Boolean isMobile() {
        return mobile;
    }

    public void setMobile(Boolean mobile) {
        this.mobile = mobile;
    }
}
