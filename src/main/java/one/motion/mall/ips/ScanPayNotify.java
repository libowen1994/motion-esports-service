package one.motion.mall.ips;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Ips")
public class ScanPayNotify implements Serializable {

    private ScanPayGateWayNotify req;

    @XmlElement(name = "GateWayRsp")
    public ScanPayGateWayNotify getReq() {
        return req;
    }

    public void setReq(ScanPayGateWayNotify req) {
        this.req = req;
    }
}
