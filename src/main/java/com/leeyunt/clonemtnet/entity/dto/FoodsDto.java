package com.leeyunt.clonemtnet.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 商品对象 Dto
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-09
 */
@Data
public class FoodsDto {

    private Integer id;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
