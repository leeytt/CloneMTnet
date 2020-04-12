package com.leeyunt.clonemtnet.controller;


import com.leeyunt.clonemtnet.service.ProductInfoService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * <p>
 * 商品管理 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-07
 */
@Api(tags = "商品管理")
@RestController
@RequestMapping("/procuteInfo")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 动态查询
     */
    @PostMapping("/selectProductInfo")
    @ApiOperation(value = "动态查询", notes = "通过各项参数查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID"),
            @ApiImplicitParam(name = "productName", value = "商品名称"),
            @ApiImplicitParam(name = "productPrice", value = "单价"),
            @ApiImplicitParam(name = "productStock", value = "库存"),
            @ApiImplicitParam(name = "productDescription", value = "描述"),
            @ApiImplicitParam(name = "productIcon", value = "图片"),
            @ApiImplicitParam(name = "productStatus", value = "商品状态 1-上架 0-下架 默认1"),
            @ApiImplicitParam(name = "categoryType", value = "类目编号"),
            @ApiImplicitParam(name = "status", value = "状态"),
            @ApiImplicitParam(name = "orderByCase", value = "排序", defaultValue = "create_time"),
            @ApiImplicitParam(name = "desc", value = "排序方式", defaultValue = "1"),
            @ApiImplicitParam(name = "pageNow", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "10")})
    public ResultUtil selectProductInfo(Integer id, String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Boolean productStatus, Integer categoryType, Boolean status, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize) {
        if (null != orderByCase) {
            if (desc == null) {
                desc = false;
            }
            orderByCase += desc ? " desc" : " asc";
        } else {
            orderByCase = "create_time";
            if (desc == null) {
                desc = false;
            }
            orderByCase += desc ? " desc" : " asc";
        }
        return productInfoService.selectProductInfo(id, productName, productPrice, productStock, productDescription, productIcon, productStatus, categoryType, status, orderByCase, desc, pageNow, pageSize);
    }

    /**
     * 添加商品
     */
    @PostMapping("/addProductInfo")
    @ApiOperation(value = "添加商品", notes = "添加记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productName", value = "商品名称", required = true),
            @ApiImplicitParam(name = "productPrice", value = "单价", required = true),
            @ApiImplicitParam(name = "productStock", value = "库存", required = true),
            @ApiImplicitParam(name = "productDescription", value = "描述"),
            @ApiImplicitParam(name = "productIcon", value = "图片"),
            @ApiImplicitParam(name = "productStatus", value = "商品状态 1-上架 0-下架 默认1"),
            @ApiImplicitParam(name = "categoryType", value = "类目编号"),
            @ApiImplicitParam(name = "status", value = "状态")})
    public ResultUtil addProductInfo(String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Boolean productStatus, Integer categoryType, Boolean status) {
        return productInfoService.addProductInfo(productName, productPrice, productStock, productDescription, productIcon, productStatus, categoryType, status);
    }

    /**
     * 动态更新商品信息
     */
    @PutMapping("/updateProductInfo")
    @ApiOperation(value = "更新商品", notes = "根据id动态更新记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true),
            @ApiImplicitParam(name = "productName", value = "商品名称"),
            @ApiImplicitParam(name = "productPrice", value = "单价"),
            @ApiImplicitParam(name = "productStock", value = "库存"),
            @ApiImplicitParam(name = "productDescription", value = "描述"),
            @ApiImplicitParam(name = "productIcon", value = "图片"),
            @ApiImplicitParam(name = "productStatus", value = "商品状态 1-上架 0-下架 默认1"),
            @ApiImplicitParam(name = "categoryType", value = "类目编号"),
            @ApiImplicitParam(name = "status", value = "状态")})
    public ResultUtil updateProductInfoById(Integer id, String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Boolean productStatus, Integer categoryType, Boolean status) {
        if (null == id) {
            return ResultUtil.ofFailMsg("商品ID不能为空");
        }
        return productInfoService.updateProductInfoById(id, productName, productPrice, productStock, productDescription, productIcon, productStatus, categoryType, status);
    }

    /**
     * 根据id查询商品信息
     */
    @GetMapping("/getProductInfo")
    @ApiOperation(value = "根据id查询", notes = "根据id查询记录")
    @ApiImplicitParam(name = "id", value = "商品ID", required = true)
    public ResultUtil getProductInfoById(Integer id) {
        return productInfoService.getProductInfoById(id);
    }

    /**
     * 根据id删除商品
     */
    @DeleteMapping("/removeProductInfo")
    @ApiOperation(value = "根据id删除", notes = "根据id删除记录")
    @ApiImplicitParam(name = "id", value = "商品ID", required = true)
    public ResultUtil removeProductInfoById(Integer id) {
        try {
            boolean res = productInfoService.removeById(id);
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
