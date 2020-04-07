package com.leeyunt.clonemtnet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leeyunt.clonemtnet.entity.Category;

/**
 * <p>
 * 商品类目表 Mapper 接口
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-07
 */
public interface CategoryDao extends BaseMapper<Category> {
    Category findById(Integer id);

    int dynamicUpdate(Category category);
}
