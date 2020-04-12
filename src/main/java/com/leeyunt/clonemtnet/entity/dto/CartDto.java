package com.leeyunt.clonemtnet.entity.dto;

import lombok.Data;

/**
 * <p>
 * 购物车对象 Dto
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-10
 */
@Data
public class CartDto {
    /*商品Id*/
    private Integer productId;

    /*购买数量*/
    private Integer productQuantity;

    public CartDto(Integer productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
