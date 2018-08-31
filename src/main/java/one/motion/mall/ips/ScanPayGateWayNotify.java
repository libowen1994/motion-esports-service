package one.motion.mall.ips;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "GateWayRsp")
public class ScanPayGateWayNotify implements Serializable {
    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }

    public ScanPayNotifyBody getBody() {
        return body;
    }

    public void setBody(ScanPayNotifyBody body) {
        this.body = body;
    }

    private ResponseHead head;
    private ScanPayNotifyBody body;
}
