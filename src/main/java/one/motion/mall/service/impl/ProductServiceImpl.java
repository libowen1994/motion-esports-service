package one.motion.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import one.motion.mall.mapper.MallProductMapper;
import one.motion.mall.model.MallProduct;
import one.motion.mall.service.IProductService;
import one.motion.mall.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private MallProductMapper productMapper;

    @Override
    public PageInfo<MallProduct> selectPage(Integer offset, Integer limit) {
        int pageNum = offset / limit + 1;
        PageHelper.startPage(pageNum,limit);
        List<MallProduct> list = productMapper.selectAll();
        if (list != null && list.size() > 0){
            for (MallProduct product:list){
                product.setIconUrl(Constants.CDN_BASE_URL + product.getIconUrl());
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public int save(MallProduct bean) {
        if (bean.getId() == null){
            //save
            return productMapper.insert(bean);
        }else {
            //update
            return productMapper.updateByPrimaryKeySelective(bean);
        }
    }
}