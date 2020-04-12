package com.leeyunt.clonemtnet.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leeyunt.clonemtnet.entity.Category;
import com.leeyunt.clonemtnet.entity.ProductInfo;
import com.leeyunt.clonemtnet.entity.dto.FoodsDto;
import com.leeyunt.clonemtnet.entity.dto.GoodsDto;
import com.leeyunt.clonemtnet.service.CategoryService;
import com.leeyunt.clonemtnet.service.ProductInfoService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 买家端商品 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-09
 */
@Api(tags = "买家端商品")
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 商品列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "商品列表", notes = "获取商品列表")
    public ResultUtil list() {
        //1、查询所有类目
        List<Category> categoryList = categoryService.list();
        //2、查询已上架的商品
        QueryWrapper<ProductInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("product_status",true);
        List<ProductInfo> productInfoList = productInfoService.list(wrapper);
        //3、数据拼装
        List<GoodsDto> goodsDtoList = new ArrayList<>();
        for (Category category:categoryList) {
            GoodsDto goodsDto = new GoodsDto();
            goodsDto.setCategoryName(category.getCategoryName());
            goodsDto.setCategoryType(category.getCategoryType());
            List<FoodsDto> foodsDtoList = new ArrayList<>();
            for (ProductInfo productInfo:productInfoList) {
                if (productInfo.getCategoryType().equals(category.getCategoryType())) {
                    FoodsDto foodsDto = new FoodsDto();
                    BeanUtils.copyProperties(productInfo, foodsDto);//拷贝对象属性
                    foodsDtoList.add(foodsDto);
                }
            }
            goodsDto.setFoodsDtoList(foodsDtoList);
            goodsDtoList.add(goodsDto);
        }
//        return ResultUtil.ofSuccess(goodsDtoList);
        return ResultUtil.of(0,"成功" ,goodsDtoList);
    }
}
