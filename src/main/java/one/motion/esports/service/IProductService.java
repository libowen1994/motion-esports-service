package one.motion.esports.service;

import com.github.pagehelper.PageInfo;
import one.motion.esports.model.MallProduct;

public interface IProductService {
    PageInfo<MallProduct> selectPage(Integer status, String keywords, Integer offset, Integer limit);

    int save(MallProduct bean);
}
