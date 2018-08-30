package one.motion.mall.controller;

import com.alibaba.fastjson.JSONObject;
import one.motion.mall.dto.NewOrderCommand;
import one.motion.mall.dto.PayType;
import one.motion.mall.dto.ToPayCommand;
import one.motion.mall.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/mall/order")
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public JSONObject create(@Valid NewOrderCommand command, HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");
        command.setPayType(PayType.SHB);
        String orderId = orderService.checkout(command.getUserId(), StringUtils.defaultString(ip, "127.0.0.1"), command.getAttach(), command.getProductId(), command.getAmount(), command.getPayType());
        JSONObject json = new JSONObject();
        json.put("orderId", orderId);
        return json;
    }

    @PostMapping("/toPay")
    public JSONObject toPay(@Valid ToPayCommand command) {
        return orderService.submit(command.getOrderId(), command.isMobile(), command.getChannel());
    }


}
