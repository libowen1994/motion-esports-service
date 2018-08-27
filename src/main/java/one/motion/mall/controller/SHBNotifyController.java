package one.motion.mall.controller;

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

    private static Logger logger = LoggerFactory.getLogger(SHBNotifyController.class);

    /**
     * notify 回执成功
     */
    public static String NOTIFY_SUCCESS = "SUC";
    /**
     * notify 回执失败
     */
    public static String NOTIFY_FAIL = "FAIL";


    @PostMapping(value = "/shb")
    @ResponseBody
    public String shbNotify(@RequestBody String json) {
        try {
            logger.info("shb异步通知报文,json:{} ", json);

            return NOTIFY_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return NOTIFY_FAIL;
        }
    }

}
