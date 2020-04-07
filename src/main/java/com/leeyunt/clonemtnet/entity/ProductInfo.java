package com.leeyunt.clonemtnet.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品信息表
 * </p>
 *
 * @author leeyunt
 * @since 2020-03-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)/*产生的setter返回的是this而不是void*/
@TableName("product_info")
@ApiModel(value="ProductInfo对象", description="商品信息表")
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "单价")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "库存")
    private Integer productStock;

    @ApiModelProperty(value = "描述")
    private String productDescription;

    @ApiModelProperty(value = "图片")
    private String productIcon;

    @ApiModelProperty(value = "商品状态 1-上架 0-下架 默认1")
    private Boolean productStatus;

    @ApiModelProperty(value = "类目编号")
    private Integer categoryType;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否有效  1有效  0无效")
    private Boolean status;
}
