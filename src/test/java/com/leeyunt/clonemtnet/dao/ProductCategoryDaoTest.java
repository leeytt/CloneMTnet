package com.leeyunt.clonemtnet.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leeyunt.clonemtnet.entity.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@SpringBootTest
class ProductCategoryDaoTest {
    @Resource
    ProductCategoryDao productCategoryDao;

    @Test
    void selectById() {
        ProductCategory productCategory = productCategoryDao.selectById(1);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional
    void insert() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("外星人最爱");
        productCategory.setCategoryType(3);
        productCategoryDao.insert(productCategory);
    }

    @Test
    void updateById() {
        ProductCategory productCategory = productCategoryDao.selectById(4);
        productCategory.setCategoryType(2);
        productCategoryDao.updateById(productCategory);
    }

    @Test
    void selectList() {
        QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
        wrapper.eq("category_type", 2);
        productCategoryDao.selectList(wrapper);
    }
}