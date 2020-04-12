package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.dao.CategoryDao;
import com.leeyunt.clonemtnet.dao.ProductInfoDao;
import com.leeyunt.clonemtnet.entity.Category;
import com.leeyunt.clonemtnet.entity.ProductInfo;
import com.leeyunt.clonemtnet.entity.dto.CartDto;
import com.leeyunt.clonemtnet.exception.BaseException;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import com.leeyunt.clonemtnet.service.ProductInfoService;
import com.leeyunt.clonemtnet.utils.Page;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息表 服务实现类
 * </p>
 *
 * @author leeyunt
 * @since 2020-03-29
 */
@Slf4j
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoDao, ProductInfo> implements ProductInfoService {
    @Resource
    private ProductInfoDao productInfoDao;

    @Resource
    private CategoryDao categoryDao;

    @Override
    public ResultUtil selectProductInfo(Integer id, String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Boolean productStatus, Integer categoryType, Boolean status, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        if (null != id)
            map.put("id", id);
        if (null!= productName)
            map.put("productName", productName);
        if (null!= productPrice)
            map.put("productPrice", productPrice);
        if (null!= productStock)
            map.put("productStock", productStock);
        if (null!= productDescription)
            map.put("productDescription", productDescription);
        if (null!= productIcon)
            map.put("productIcon", productIcon);
        if (null!= productStatus)
            map.put("productStatus", productStatus);
        if (null!= categoryType)
            map.put("categoryType", categoryType);
        if (null!= status)
            map.put("status", status);
        if (null != orderByCase)
            map.put("orderByCase", orderByCase);
        if (null != pageNow && null != pageSize) {
            map.put("startPos", (pageNow - 1) * pageSize);
            map.put("pageSize", pageSize);
        }
        try {
            long count = productInfoDao.getCount(map);
            if (count ==0 ) {
                return ResultUtil.ofFailMsg("未查询到结果");
            }
            List<ProductInfo> list = productInfoDao.dynamicSelect(map);
            HashMap<String, Object> data = new HashMap<>();
            if (null != list) {
                for (ProductInfo productInfo: list) {
                    Integer type= productInfoDao.findById(productInfo.getId()).getCategoryType();
                    if (null != type) {
                        Category category = categoryDao.findByType(type);
                        productInfo.setCategory(category);
                    }
                }
            }
            pageSize = null != pageSize ? pageSize : (int) (count);
            pageNow = null != pageNow ? pageNow : 1;
            if (pageSize != 0) {
                Page page = new Page(count, pageNow);
                page.setPageSize(pageSize);
                data.put("page", page);
            }
            if (list.size() > 0) {
                data.put("list", list);
                return ResultUtil.ofSuccess(data);
            } else {
                return ResultUtil.ofStatusCode(StatusEnum.FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ofFailMsg("dynamic查询User出错！");
        }
    }

    @Override
    public ResultUtil addProductInfo(String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Boolean productStatus, Integer categoryType, Boolean status) {
        ProductInfo productInfo = new ProductInfo();
        comParam(productName, productPrice, productStock, productDescription, productIcon, productStatus, categoryType, status, productInfo);
        try {
            int res = productInfoDao.insert(productInfo);
            if (res == 1) {
                return ResultUtil.ofSuccessMsg("添加成功");
            } else {
                return ResultUtil.ofFailMsg("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ofFailMsg("insertUser出错！");
        }
    }

    @Override
    public ResultUtil updateProductInfoById(Integer id, String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Boolean productStatus, Integer categoryType, Boolean status) {
        if (null != productInfoDao.findById(id)) {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setId(id);
            comParam(productName, productPrice, productStock, productDescription, productIcon, productStatus, categoryType, status, productInfo);
            try {
                int res = productInfoDao.dynamicUpdate(productInfo);
                if (res == 1) {
                    return ResultUtil.ofSuccessMsg("更新成功");
                } else {
                    return ResultUtil.ofFailMsg("更新失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.ofFailMsg("dynamicUpdateProductInfo出错！");
            }
        }else {
            return ResultUtil.ofFailMsg("ID不存在");
        }
    }

    /**
     * 加库存
     */
    @Override
    @Transactional
    public void increaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto:cartDtoList) {
            ProductInfo productInfo = productInfoDao.findById(cartDto.getProductId());
            if (productInfo == null) {
                log.error("【返还库存】商品不存在");
                throw new BaseException(StatusEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDto.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoDao.dynamicUpdate(productInfo);
        }
    }

    /**
     * 减库存
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto:cartDtoList) {
            ProductInfo productInfo = productInfoDao.findById(cartDto.getProductId());
            if (productInfo == null) {
                log.error("【减库存】商品不存在");
                throw new BaseException(StatusEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDto.getProductQuantity();
            if (result <0 ) {
                log.error("【减库存】商品库存不足");
                throw new BaseException(StatusEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoDao.dynamicUpdate(productInfo);
        }
    }

    /**
     * 根据商品Id获取商品信息
     */
    @Override
    public ResultUtil getProductInfoById(Integer id) {
        ProductInfo productInfo = productInfoDao.findById(id);
        if (null != productInfo) {
            if (null != productInfo.getCategoryType()) {
                Category category = categoryDao.findByType(productInfo.getCategoryType());
                productInfo.setCategory(category);
            }
            return ResultUtil.ofSuccess(productInfo);
        }
        return ResultUtil.ofFailMsg("未能获取id=" + id + "的数据");
    }

    /**
     * 公共Setter参数
     */
    private void comParam(String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Boolean productStatus, Integer categoryType, Boolean status, ProductInfo productInfo) {
        productInfo.setProductName(productName);
        productInfo.setProductPrice(productPrice);
        productInfo.setProductStock(productStock);
        productInfo.setProductDescription(productDescription);
        productInfo.setProductIcon(productIcon);
        productInfo.setProductStatus(productStatus);
        productInfo.setCategoryType(categoryType);
        productInfo.setStatus(status);
    }
}
