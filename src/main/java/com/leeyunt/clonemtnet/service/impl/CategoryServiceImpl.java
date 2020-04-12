package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.dao.CategoryDao;
import com.leeyunt.clonemtnet.entity.Category;
import com.leeyunt.clonemtnet.service.CategoryService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 后台角色表 服务实现类
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
    @Resource
    private CategoryDao categoryDao;

    /**
     * 添加商品类目
     */
    @Override
    public ResultUtil addCategory(Integer categoryType, String categoryName, Boolean status) {
        Category category = new Category();
        category.setCategoryType(categoryType);
        category.setCategoryName(categoryName);
        category.setStatus(status);
        try {
            int res = categoryDao.insert(category);
            if (res == 1) {
                return ResultUtil.ofSuccessMsg("添加成功");
            } else {
                return ResultUtil.ofFailMsg("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ofFailMsg("insertCategory出错！");
        }
    }

    /**
     * 动态更新商品类目
     */
    @Override
    public ResultUtil updateCategoryById(Integer id, Integer categoryType, String categoryName, Boolean status) {
        Category category = categoryDao.findById(id);
        if (null != category) {
            category.setId(id);
            category.setCategoryType(categoryType);
            category.setCategoryName(categoryName);
            category.setStatus(status);
            try {
                int res = categoryDao.dynamicUpdate(category);
                if (res == 1) {
                    return ResultUtil.ofSuccessMsg("更新成功");
                } else {
                    return ResultUtil.ofFailMsg("更新失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.ofFailMsg("dynamicUpdateCategory出错！");
            }
        }else {
            return ResultUtil.ofFailMsg("ID不存在");
        }
    }
}
