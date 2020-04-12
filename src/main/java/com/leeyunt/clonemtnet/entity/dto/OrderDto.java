package com.leeyunt.clonemtnet.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.leeyunt.clonemtnet.entity.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单对象 Dto
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-09
 */
@Data
public class OrderDto {
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<OrderDetail> orderDetailList; //订单详情list
}
