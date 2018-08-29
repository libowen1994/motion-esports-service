package one.motion.mall.service;

import com.github.pagehelper.PageInfo;
import one.motion.mall.model.MallProduct;

public interface IProductService {
    PageInfo<MallProduct> selectPage(Integer offset, Integer limit);

    int save(MallProduct bean);
}
