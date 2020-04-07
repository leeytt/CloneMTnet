package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.dao.ProductInfoDao;
import com.leeyunt.clonemtnet.entity.ProductInfo;
import com.leeyunt.clonemtnet.service.ProductInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品信息表 服务实现类
 * </p>
 *
 * @author leeyunt
 * @since 2020-03-29
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoDao, ProductInfo> implements ProductInfoService {

}
