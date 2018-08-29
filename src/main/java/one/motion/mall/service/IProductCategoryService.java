package one.motion.mall.service;

import com.github.pagehelper.PageInfo;
import one.motion.mall.model.MallProductCategory;

public interface IProductCategoryService {
    PageInfo<MallProductCategory> getAllCategorys(int offset, int limit);

    int saveOrUpdate(MallProductCategory bean);
}
