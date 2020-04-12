package com.leeyunt.clonemtnet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leeyunt.clonemtnet.entity.ProductInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息表 Mapper 接口
 * </p>
 *
 * @author leeyunt
 * @since 2020-03-29
 */
public interface ProductInfoDao extends BaseMapper<ProductInfo> {

    long getCount(Map<String, Object> map);

    List<ProductInfo> dynamicSelect(Map<String, Object> map);

    ProductInfo findById(Integer id);

    int dynamicUpdate(ProductInfo productInfo);
}
