package one.motion.mall.controller;

import com.github.pagehelper.PageInfo;
import one.motion.mall.model.MallOrder;
import one.motion.mall.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mall/order")
public class OrderController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IOrderService orderService;

    @RequestMapping()
    public ResponseEntity<PageInfo> getList(MallOrder order,Integer offset, Integer limit) {
        if (offset == null) {
            offset = 0;
        }
        if (limit == null || limit <= 0) {
            limit = 20;
        }
        PageInfo<MallOrder> data = orderService.selectPage(order,offset, limit);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
