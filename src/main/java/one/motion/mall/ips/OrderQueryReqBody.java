package one.motion.mall.ips;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "body")
public class OrderQueryReqBody implements Serializable {
    private String MerBillNo;
    private String Date;
    private String Amount;

    public String getMerBillNo() {
        return MerBillNo;
    }

    public void setMerBillNo(String merBillNo) {
        MerBillNo = merBillNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
