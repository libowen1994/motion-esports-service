package one.motion.mall.ips;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "OrderQueryReq")
public class OrderQueryReq implements Serializable {
    private RequestHead head;
    private OrderQueryReqBody body;

    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }

    public OrderQueryReqBody getBody() {
        return body;
    }

    public void setBody(OrderQueryReqBody body) {
        this.body = body;
    }
}
