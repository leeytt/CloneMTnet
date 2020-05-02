package com.leeyunt.clonemtnet.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 订单主表
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("order_master")
@ApiModel(value="OrderMaster对象", description="订单主表")
public class OrderMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "买家姓名")
    private String buyerName;

    @ApiModelProperty(value = "买家电话")
    private String buyerPhone;

    @ApiModelProperty(value = "买家地址")
    private String buyerAddress;

    @ApiModelProperty(value = "买家微信openid")
    private String buyerOpenid;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "订单状态 0-新订单 1-已完结 2-已取消 默认0")
    private Integer orderStatus;

    @ApiModelProperty(value = "支付状态 0-待支付 1-已支付 默认0")
    private Integer payStatus;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否有效  1有效  0无效")
    private Boolean status;

}
