package one.motion.esports.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import one.motion.esports.config.KafkaConfig;
import one.motion.esports.mapper.MallProductMapper;
import one.motion.esports.model.MallProduct;
import one.motion.esports.service.IProductService;
import one.motion.esports.utils.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MallProductMapper productMapper;

    @Override
    public PageInfo<MallProduct> selectPage(Integer status, String keywords, Integer offset, Integer limit) {
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

    @KafkaListener(topics = KafkaConfig.ESPORTS_EXCHANGE_TOPIC)
    public void processExchangeNotify(ConsumerRecord<?, String> cr) {
        Optional<String> kafkaMessage = Optional.ofNullable(cr.value());
        if (kafkaMessage.isPresent()) {
            String message = kafkaMessage.get();
            logger.info("record =" + cr);
            logger.info("message =" + message);
            JSONObject jsonObject = JSONObject.parseObject(message);
            logger.info("json = " + jsonObject);
        }
    }

}