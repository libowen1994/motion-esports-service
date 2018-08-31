package one.motion.mall.controller;

import one.motion.mall.dto.PayType;
import one.motion.mall.service.IOrderService;
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
    private static String NOTIFY_SUCCESS = "ipscheckok";
    /**
     * notify 回执失败
     */
    private static String NOTIFY_FAIL = "ipscheckerror";
    private final IOrderService orderService;

    public IPSNotifyController(IOrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping(value = "/ips")
    @ResponseBody
    public String ipsNotify(String paymentResult) {
        try {
            logger.info("ips异步通知报文,xml:{} ", paymentResult);
            orderService.paymentNotify(paymentResult, PayType.IPS);
            return NOTIFY_SUCCESS;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return NOTIFY_FAIL;
        }
    }

}
