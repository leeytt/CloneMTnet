package com.leeyunt.clonemtnet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leeyunt.clonemtnet.entity.ProductInfo;
import com.leeyunt.clonemtnet.entity.dto.CartDto;
import com.leeyunt.clonemtnet.utils.ResultUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 商品信息表 服务类
 * </p>
 *
 * @author leeyunt
 * @since 2020-03-29
 */
public interface ProductInfoService extends IService<ProductInfo> {

    ResultUtil selectProductInfo(Integer id, String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Boolean productStatus, Integer categoryType, Boolean status, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize);

    ResultUtil addProductInfo(String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Boolean productStatus, Integer categoryType, Boolean status);

    ResultUtil updateProductInfoById(Integer id, String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Boolean productStatus, Integer categoryType, Boolean status);

    ResultUtil getProductInfoById(Integer id);

    //加库存
    void increaseStock(List<CartDto> cartDtoList);

    //减库存
    void decreaseStock(List<CartDto> cartDtoList);
}
