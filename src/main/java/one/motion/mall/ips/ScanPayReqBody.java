package one.motion.mall.ips;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "body")
public class ScanPayReqBody implements Serializable {
    private String MerBillNo;
    private String GatewayType;
    private String MerType;
    private String SubMerCode;
    private String Date;
    private String CurrencyType;
    private String Amount;
    private String SpbillCreateIp;
    private String Attach;
    private String RetEncodeType;
    private String ServerUrl;
    private String BillEXP;
    private String GoodsName;

    public String getMerBillNo() {
        return MerBillNo;
    }

    public void setMerBillNo(String merBillNo) {
        MerBillNo = merBillNo;
    }

    public String getGatewayType() {
        return GatewayType;
    }

    public void setGatewayType(String gatewayType) {
        GatewayType = gatewayType;
    }

    public String getMerType() {
        return MerType;
    }

    public void setMerType(String merType) {
        MerType = merType;
    }

    public String getSubMerCode() {
        return SubMerCode;
    }

    public void setSubMerCode(String subMerCode) {
        SubMerCode = subMerCode;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
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

    public String getSpbillCreateIp() {
        return SpbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        SpbillCreateIp = spbillCreateIp;
    }

    public String getAttach() {
        return Attach;
    }

    public void setAttach(String attach) {
        Attach = attach;
    }

    public String getRetEncodeType() {
        return RetEncodeType;
    }

    public void setRetEncodeType(String retEncodeType) {
        RetEncodeType = retEncodeType;
    }

    public String getServerUrl() {
        return ServerUrl;
    }

    public void setServerUrl(String serverUrl) {
        ServerUrl = serverUrl;
    }

    public String getBillEXP() {
        return BillEXP;
    }

    public void setBillEXP(String billEXP) {
        BillEXP = billEXP;
    }

    public String getGoodsName() {
        return GoodsName;
    }

    public void setGoodsName(String goodsName) {
        GoodsName = goodsName;
    }
}
