package com.leeyunt.clonemtnet.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 类目（包含商品） 视图对象
 * </p>
 *
 * @author leeyunt
 * @since 2020-03-29
 */
@Data
public class CategoryVO {

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
