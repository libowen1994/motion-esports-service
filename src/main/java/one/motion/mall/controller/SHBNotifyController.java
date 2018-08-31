package one.motion.mall.controller;

import one.motion.mall.dto.PayType;
import one.motion.mall.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/mall/payment/notify")
public class SHBNotifyController {

    private final IOrderService orderService;

    private static Logger logger = LoggerFactory.getLogger(SHBNotifyController.class);

    /**
     * notify 回执成功
     */
    private static String NOTIFY_SUCCESS = "SUC";
    /**
     * notify 回执失败
     */
    private static String NOTIFY_FAIL = "FAIL";

    public SHBNotifyController(IOrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping(value = "/shb")
    @ResponseBody
    public String shbNotify(@RequestBody String json) {
        try {
            logger.info("shb异步通知报文,json:{} ", json);
            orderService.paymentNotify(json, PayType.SHB);
            return NOTIFY_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return NOTIFY_FAIL;
        }
    }

}
