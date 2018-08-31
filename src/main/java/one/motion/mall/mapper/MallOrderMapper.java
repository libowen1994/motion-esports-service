package one.motion.mall.mapper;

import one.motion.mall.model.MallOrder;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MallOrderMapper extends Mapper<MallOrder>, MySqlMapper<MallOrder> {
}