package one.motion.mall.service;

import com.github.pagehelper.PageInfo;
import one.motion.mall.model.MallProductCategory;

public interface IProductCategoryService {
    PageInfo<MallProductCategory> getAllCategorys(String keywords,Integer status,int offset, int limit);

    int saveOrUpdate(MallProductCategory bean);
}
