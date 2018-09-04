package one.motion.mall.controller;

import com.github.pagehelper.PageInfo;
import one.motion.mall.dto.JsonResult;
import one.motion.mall.model.MallProductCategory;
import one.motion.mall.service.IProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mall/productCategory")
public class MallProductCategoryController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IProductCategoryService categoryService;

    @GetMapping()
    public ResponseEntity<PageInfo> productCategory(String keywords,Integer status,Integer offset, Integer limit){
        logger.info("offset -> {}, limit -> {}",offset,limit);
        if (offset == null){
            offset = 0;
        }
        if (limit == null){
            limit = 1000;
        }
        PageInfo<MallProductCategory> categorys = categoryService.getAllCategorys(keywords,status,offset, limit);
        return new ResponseEntity<>(categorys, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(MallProductCategory bean){
        logger.info("save MallProductCategory info ->",bean);

        JsonResult jsonResult = null;

        try {
            int ret = categoryService.saveOrUpdate(bean);
            if (ret == 1){
                jsonResult = new JsonResult("保存成功");
            }else {
                jsonResult = new JsonResult(false,"保存失败");
            }
        }catch (Exception e){
            logger.info("save MallProductCategory error ->",e.getMessage());
            jsonResult = new JsonResult(false,e.getMessage());
        }

        logger.info("save MallProductCategory result",jsonResult);

        return new ResponseEntity<>(jsonResult,HttpStatus.OK);
    }
}