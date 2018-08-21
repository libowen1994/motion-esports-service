package one.motion.mall.mapper;

import one.motion.mall.model.MallProduct;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MallProductMapper extends Mapper<MallProduct>, MySqlMapper<MallProduct> {
}