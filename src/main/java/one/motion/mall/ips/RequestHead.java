package one.motion.mall.ips;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "head")
public class RequestHead implements Serializable {
    private String Version;
    private String MerCode;
    private String MerName;
    private String Account;
    private String MsgId;
    private String ReqDate;
    private String Signature;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getMerCode() {
        return MerCode;
    }

    public void setMerCode(String merCode) {
        MerCode = merCode;
    }

    public String getMerName() {
        return MerName;
    }

    public void setMerName(String merName) {
        MerName = merName;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getReqDate() {
        return ReqDate;
    }

    public void setReqDate(String reqDate) {
        ReqDate = reqDate;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }
}
