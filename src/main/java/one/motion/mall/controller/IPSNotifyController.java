package one.motion.mall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/mall/payment/notify")
public class IPSNotifyController {

    private static Logger logger = LoggerFactory.getLogger(IPSNotifyController.class);

    /**
     * notify 回执成功
     */
    public static String NOTIFY_SUCCESS = "ipscheckok";
    /**
     * notify 回执失败
     */
    public static String NOTIFY_FAIL = "ipscheckerror";


    @PostMapping(value = "/ips")
    @ResponseBody
    public String shbNotify(String paymentResult) {
        try {
            logger.info("ips异步通知报文,xml:{} ", paymentResult);

            return NOTIFY_SUCCESS;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return NOTIFY_FAIL;
        }
    }

}
