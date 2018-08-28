package one.motion.mall.ips;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Ips")
public class ScanPayReq implements Serializable {

    private ScanPayGateWayReq req;

    @XmlElement(name = "GateWayReq")
    public ScanPayGateWayReq getReq() {
        return req;
    }

    public void setReq(ScanPayGateWayReq req) {
        this.req = req;
    }
}
