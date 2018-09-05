package one.motion.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import one.motion.mall.mapper.MallProductCategoryMapper;
import one.motion.mall.model.MallProductCategory;
import one.motion.mall.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductCategoryServiceImpl implements IProductCategoryService {
    @Autowired
    private MallProductCategoryMapper categoryMapper;

    @Override
    public PageInfo<MallProductCategory> getAllCategorys(String keywords,Integer status,int offset, int limit) {

        int pageNum = offset / limit + 1;
        PageHelper.startPage(pageNum,limit);

        Weekend weekend = Weekend.of(MallProductCategory.class);
        WeekendCriteria<MallProductCategory, Object> where = weekend.weekendCriteria();
        if (!StringUtils.isEmpty(keywords)){
            where.andLike(MallProductCategory :: getName, "%" + keywords + "%");
            where.orLike(MallProductCategory :: getCategoryCode, "%" + keywords + "%");
        }

        Example ex = new Example(MallProductCategory.class);
        ex.and(where);
        if (status != null){
            ex.and().andEqualTo("enabled",status);
        }
        List<MallProductCategory> list = categoryMapper.selectByExample(ex);
        return new PageInfo<>(list);
    }

    @Override
    public int saveOrUpdate(MallProductCategory bean) {
        if (bean.getId() == null) {
            //save
            MallProductCategory category = new MallProductCategory();
            category.setLangCode(bean.getLangCode());
            category.setCategoryCode(bean.getCategoryCode());
            List<MallProductCategory> select = categoryMapper.select(category);
            if (select != null && select.size() > 0) {
                return -1;
            }
            bean.setCreatedAt(new Date());
            bean.setUpdatedAt(new Date());
            bean.setUuid(UUID.randomUUID().toString());
            return categoryMapper.insert(bean);
        } else {
            //update
            if (bean.getName() == null) {
                return categoryMapper.updateByPrimaryKeySelective(bean);
            }
            MallProductCategory category = new MallProductCategory();
            category.setLangCode(bean.getLangCode());
            category.setCategoryCode(bean.getCategoryCode());
            List<MallProductCategory> select = categoryMapper.select(category);
            if (select != null && select.size() == 1) {
                return categoryMapper.updateByPrimaryKeySelective(bean);
            }
            return 0;
        }
    }
}