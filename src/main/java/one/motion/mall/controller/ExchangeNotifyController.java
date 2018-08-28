package one.motion.mall.controller;

import one.motion.mall.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/mall/exchange")
public class ExchangeNotifyController {

    private static Logger logger = LoggerFactory.getLogger(ExchangeNotifyController.class);

    /**
     * notify 回执成功
     */
    private static String NOTIFY_SUCCESS = "success";
    /**
     * notify 回执失败
     */
    private static String NOTIFY_FAIL = "error";
    private final IOrderService orderService;

    public ExchangeNotifyController(IOrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping(value = "/notify")
    @ResponseBody
    public String notify(String result) {
        try {
            logger.info("exchange异步通知报文,xml:{} ", result);
            orderService.exchangeNotify(result);
            return NOTIFY_SUCCESS;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return NOTIFY_FAIL;
        }
    }

}
