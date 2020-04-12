package com.leeyunt.clonemtnet.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 类目商品对象 Dto
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-09
 */
@Data
public class GoodsDto {
    /**
     * 类目名
     */
    @JsonProperty("name")
    private String categoryName;

    /**
     * 类目编号
     */
    @JsonProperty("type")
    private Integer categoryType;

    /**
     * 商品list
     */
    @JsonProperty("foods")
    private List<FoodsDto> foodsDtoList;
}
