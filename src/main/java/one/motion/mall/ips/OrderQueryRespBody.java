package one.motion.mall.ips;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "body")
public class OrderQueryRespBody implements Serializable {
    private String MerBillNo;
    private String IpsBillNo;
    private String TradeType;
    private String Currency;
    private String Amount;
    private String MerBillDate;
    private String IpsBillTime;
    private String Attach;
    private String Status;

    public String getMerBillNo() {
        return MerBillNo;
    }

    public void setMerBillNo(String merBillNo) {
        MerBillNo = merBillNo;
    }

    public String getIpsBillNo() {
        return IpsBillNo;
    }

    public void setIpsBillNo(String ipsBillNo) {
        IpsBillNo = ipsBillNo;
    }

    public String getTradeType() {
        return TradeType;
    }

    public void setTradeType(String tradeType) {
        TradeType = tradeType;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getMerBillDate() {
        return MerBillDate;
    }

    public void setMerBillDate(String merBillDate) {
        MerBillDate = merBillDate;
    }

    public String getIpsBillTime() {
        return IpsBillTime;
    }

    public void setIpsBillTime(String ipsBillTime) {
        IpsBillTime = ipsBillTime;
    }

    public String getAttach() {
        return Attach;
    }

    public void setAttach(String attach) {
        Attach = attach;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
