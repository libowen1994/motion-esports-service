package one.motion.mall.ips;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Ips")
public class OrderQueryResp implements Serializable {

    private OrderQueryRsp req;

    @XmlElement(name = "OrderQueryRsp")
    public OrderQueryRsp getReq() {
        return req;
    }

    public void setReq(OrderQueryRsp req) {
        this.req = req;
    }
}
