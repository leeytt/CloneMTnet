package com.leeyunt.clonemtnet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leeyunt.clonemtnet.entity.Category;
import com.leeyunt.clonemtnet.utils.ResultUtil;

/**
 * <p>
 * 商品类目管理 服务类
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-07
 */
public interface CategoryService extends IService<Category> {
    /**
     * 添加商品类目
     */
    ResultUtil addCategory(Integer categoryType, String categoryName, Boolean status);

    /**
     * 动态更新商品类目
     */
    ResultUtil updateCategoryById(Integer id, Integer categoryType, String categoryName, Boolean status);
}
