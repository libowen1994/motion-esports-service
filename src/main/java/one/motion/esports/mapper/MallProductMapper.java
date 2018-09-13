package one.motion.esports.mapper;

import one.motion.esports.model.MallProduct;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MallProductMapper extends Mapper<MallProduct>, MySqlMapper<MallProduct> {
}