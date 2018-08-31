package one.motion.mall.ips;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Ips")
public class OrderQuery implements Serializable {

    private OrderQueryReq req;

    @XmlElement(name = "OrderQueryReq")
    public OrderQueryReq getReq() {
        return req;
    }

    public void setReq(OrderQueryReq req) {
        this.req = req;
    }
}
