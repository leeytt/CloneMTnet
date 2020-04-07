package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.dao.ProductCategoryDao;
import com.leeyunt.clonemtnet.entity.ProductCategory;
import com.leeyunt.clonemtnet.service.ProductCategoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品类目表 服务实现类
 * </p>
 *
 * @author leeyunt
 * @since 2020-03-29
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryDao, ProductCategory> implements ProductCategoryService {

}
