package one.motion.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import one.motion.mall.mapper.MallProductCategoryMapper;
import one.motion.mall.mapper.MallProductMapper;
import one.motion.mall.model.MallProduct;
import one.motion.mall.service.IProductService;
import one.motion.mall.utils.Constants;
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
public class ProductServiceImpl implements IProductService {
    @Autowired
    private MallProductMapper productMapper;
    @Autowired
    private MallProductCategoryMapper categoryMapper;

    @Override
    public PageInfo<MallProduct> selectPage(Integer status,String keywords,Integer offset, Integer limit) {
        int pageNum = offset / limit + 1;
        PageHelper.startPage(pageNum,limit);

        Weekend weekend = Weekend.of(MallProduct.class);
        WeekendCriteria<MallProduct, Object> where = weekend.weekendCriteria();
        if (!StringUtils.isEmpty(keywords)){
            where.andLike(MallProduct :: getName, "%" + keywords + "%");
            where.orLike(MallProduct :: getCategoryCode, "%" + keywords + "%");
        }

        Example ex = new Example(MallProduct.class);
        ex.and(where);
        if (status != null){
            ex.and().andEqualTo("enabled",status);
        }
        List<MallProduct> list = productMapper.selectByExample(ex);
        if (list != null && list.size() > 0){
            for (MallProduct product:list){
                product.setIconUrl(Constants.CDN_BASE_URL + product.getIconUrl());
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public int save(MallProduct bean) {
        String code = bean.getCategoryCode();
        String[] split = null;
        if (code != null){
            split = code.split("#");
        }
        if (split == null || split.length != 2 || "defaultCode".equals(split[0])){
            return -1;
        }
        if (bean.getId() == null){
            bean.setCategoryCode(split[0]);
            bean.setLangCode(split[1]);
            bean.setUuid(UUID.randomUUID().toString());
            bean.setCreatedAt(new Date());
            bean.setUpdatedAt(new Date());
            return productMapper.insert(bean);
        }else {
            //update
            if (bean.getCategoryCode() != null){
                bean.setCategoryCode(split[0]);
                bean.setLangCode(split[1]);
            }
            return productMapper.updateByPrimaryKeySelective(bean);
        }
    }
}