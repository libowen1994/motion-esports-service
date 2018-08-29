package one.motion.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import one.motion.mall.mapper.MallProductCategoryMapper;
import one.motion.mall.model.MallProductCategory;
import one.motion.mall.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {
    @Autowired
    private MallProductCategoryMapper categoryMapper;

    @Override
    public PageInfo<MallProductCategory> getAllCategorys(int offset, int limit) {
        int pageNum = offset / limit + 1;
        PageHelper.startPage(pageNum,limit);
        List<MallProductCategory> list = categoryMapper.selectAll();
        return new PageInfo<>(list);
    }

    @Override
    public int saveOrUpdate(MallProductCategory bean) {
        if (bean.getId() == null){
            //save
            return categoryMapper.insert(bean);
        }else {
            //update
            return categoryMapper.updateByPrimaryKeySelective(bean);
        }
    }
}