package one.motion.mall.dto;

import java.util.Date;

public class ExchangeResult {
    private String orderId;
    private String exchangeOrderId;
    private ExchangeStatus status;
    private Date time;
    private String resultCode;
    private String resultMessage;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ExchangeStatus getStatus() {
        return status;
    }

    public void setStatus(ExchangeStatus status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getExchangeOrderId() {
        return exchangeOrderId;
    }

    public void setExchangeOrderId(String exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }
}
