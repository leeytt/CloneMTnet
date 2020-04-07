package com.leeyunt.clonemtnet.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 商品详情 视图对象
 * </p>
 *
 * @author leeyunt
 * @since 2020-03-29
 */
@Data
public class ProductInfoVO {
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
