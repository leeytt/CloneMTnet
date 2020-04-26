package com.leeyunt.clonemtnet.controller;


import com.leeyunt.clonemtnet.service.CategoryService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品类目管理 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-07
 */
@Api(tags = "商品类目管理")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取列表
     */
    @GetMapping("getAll")
    @ApiOperation(value="获取商品类目列表",notes="得到所有商品类目数据")
    public ResultUtil loadAll(){
        if (0==categoryService.count()) {
            return ResultUtil.ofFailMsg("未查询到结果");
        }
        return ResultUtil.ofSuccess(categoryService.list());
    }

    /**
     * 添加商品类目
     */
    @PostMapping("/addCategory")
    @ApiOperation(value = "添加商品类目", notes = "添加记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryType", value = "类目编号", defaultValue = "1", required = true),
            @ApiImplicitParam(name = "categoryName", value = "类目名称", defaultValue = "热销榜", required = true),
            @ApiImplicitParam(name = "status", value = "状态")})
    public ResultUtil addCategory(Integer categoryType, String categoryName, Boolean status) {
        if (null == categoryType) {
            return ResultUtil.ofFailMsg("类目编号不能为空");
        }
        if (null == categoryName) {
            return ResultUtil.ofFailMsg("类目名称不能为空");
        }
        return categoryService.addCategory(categoryType, categoryName, status);
    }

    /**
     * 动态更新商品类目
     */
    @PutMapping("/updateCategory")
    @ApiOperation(value = "更新商品类目", notes = "根据id动态更新记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品类目ID", required = true),
            @ApiImplicitParam(name = "categoryType", value = "类目编号"),
            @ApiImplicitParam(name = "categoryName", value = "类目名称"),
            @ApiImplicitParam(name = "status", value = "状态")})
    public ResultUtil updateRoleById(Integer id, Integer categoryType, String categoryName, Boolean status) {
        if (null == id) {
            return ResultUtil.ofFailMsg("商品类目ID不能为空");
        }
        return categoryService.updateCategoryById(id, categoryType, categoryName, status);
    }

    /**
     * 根据id删除商品类目
     */
    @DeleteMapping("/removeCategory")
    @ApiOperation(value = "根据id删除", notes = "根据id删除记录")
    @ApiImplicitParam(name = "id", value = "商品类目ID", required = true)
    public ResultUtil removeCategoryById(Integer id) {
        try {
            boolean res = categoryService.removeById(id);
            if (res) {
                return ResultUtil.ofSuccessMsg("删除成功");
            }
            return ResultUtil.ofFailMsg("删除失败");
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ofFailMsg("删除User出错！");
        }
    }

}
