package one.motion.mall.ips;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "OrderQueryRsp")
public class OrderQueryRsp implements Serializable {
    private ResponseHead head;
    private OrderQueryRespBody body;

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }

    public OrderQueryRespBody getBody() {
        return body;
    }

    public void setBody(OrderQueryRespBody body) {
        this.body = body;
    }
}
