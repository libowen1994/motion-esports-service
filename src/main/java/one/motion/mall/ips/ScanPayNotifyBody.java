package one.motion.mall.ips;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "body")
public class ScanPayNotifyBody implements Serializable {
    private String MerBillNo;
    private String CurrencyType;
    private String Amount;
    private String Date;
    private String Status;
    private String Msg;
    private String Attach;
    private String IpsBillNo;
    private String IpsTradeNo;
    private String RetEncodeType;
    private String BankBillNo;
    private String ResultType;
    private String IpsBillTime;

    public String getMerBillNo() {
        return MerBillNo;
    }

    public void setMerBillNo(String merBillNo) {
        MerBillNo = merBillNo;
    }

    public String getCurrencyType() {
        return CurrencyType;
    }

    public void setCurrencyType(String currencyType) {
        CurrencyType = currencyType;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getAttach() {
        return Attach;
    }

    public void setAttach(String attach) {
        Attach = attach;
    }

    public String getIpsBillNo() {
        return IpsBillNo;
    }

    public void setIpsBillNo(String ipsBillNo) {
        IpsBillNo = ipsBillNo;
    }

    public String getIpsTradeNo() {
        return IpsTradeNo;
    }

    public void setIpsTradeNo(String ipsTradeNo) {
        IpsTradeNo = ipsTradeNo;
    }

    public String getRetEncodeType() {
        return RetEncodeType;
    }

    public void setRetEncodeType(String retEncodeType) {
        RetEncodeType = retEncodeType;
    }

    public String getBankBillNo() {
        return BankBillNo;
    }

    public void setBankBillNo(String bankBillNo) {
        BankBillNo = bankBillNo;
    }

    public String getResultType() {
        return ResultType;
    }

    public void setResultType(String resultType) {
        ResultType = resultType;
    }

    public String getIpsBillTime() {
        return IpsBillTime;
    }

    public void setIpsBillTime(String ipsBillTime) {
        IpsBillTime = ipsBillTime;
    }
}
