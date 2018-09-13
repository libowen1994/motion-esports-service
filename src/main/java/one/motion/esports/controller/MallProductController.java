package one.motion.esports.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import one.motion.esports.config.KafkaConfig;
import one.motion.esports.dto.JsonResult;
import one.motion.esports.model.MallProduct;
import one.motion.esports.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mall/product")
public class MallProductController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IProductService productService;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping()
    public ResponseEntity<PageInfo> getList(Integer status,String keywords,Integer offset, Integer limit) {
        logger.info("query MallProduct status -> {} , keywords -> {}",status,keywords);
        if (offset == null) {
            offset = 0;
        }
        if (limit == null || limit <= 0) {
            limit = 20;
        }
        PageInfo<MallProduct> data = productService.selectPage(status,keywords,offset, limit);
        kafkaTemplate.send(KafkaConfig.ESPORTS_EXCHANGE_TOPIC, JSON.toJSONString(data));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(MallProduct bean){
        logger.info("save mallProduct -> ",bean);
        JsonResult jsonResult = null;
        try {
            int i = productService.save(bean);
            if (i > 0){
                jsonResult = new JsonResult("保存成功");
            }else if(i == -1){
                jsonResult = new JsonResult(false,"请选择商品类型");
            }else {
                jsonResult = new JsonResult(false,"保存失败");
            }
        }catch (Exception e){
            jsonResult = new JsonResult(false,e.getMessage());
        }
        return new ResponseEntity<>(jsonResult,HttpStatus.OK);
    }
}