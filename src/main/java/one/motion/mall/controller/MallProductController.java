package one.motion.mall.controller;

import com.github.pagehelper.PageInfo;
import one.motion.mall.dto.JsonResult;
import one.motion.mall.model.MallProduct;
import one.motion.mall.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mall/product")
public class MallProductController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IProductService productService;

    @RequestMapping()
    public ResponseEntity<PageInfo> getList(Integer offset, Integer limit) {
        if (offset == null) {
            offset = 0;
        }
        if (limit == null || limit <= 0) {
            limit = 20;
        }
        PageInfo<MallProduct> data = productService.selectPage(offset, limit);
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
            }else {
                jsonResult = new JsonResult(false,"保存失败");
            }
        }catch (Exception e){
            jsonResult = new JsonResult(false,e.getMessage());
        }
        return new ResponseEntity<>(jsonResult,HttpStatus.OK);
    }
}