package one.motion.mall.ips;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Ips")
public class ScanPayResp implements Serializable {

    private ScanPayGateWayResp req;

    @XmlElement(name = "GateWayRsp")
    public ScanPayGateWayResp getReq() {
        return req;
    }

    public void setReq(ScanPayGateWayResp req) {
        this.req = req;
    }
}
