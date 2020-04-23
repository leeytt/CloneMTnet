package com.leeyunt.clonemtnet.controller;

import com.leeyunt.clonemtnet.service.OrderMasterService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>
 * 订单管理 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-22
 */
@Api(tags = "订单管理")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 动态查询
     */
    @PostMapping("/selectOrder")
    @ApiOperation(value = "动态查询", notes = "通过各项参数查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单ID"),
            @ApiImplicitParam(name = "buyerName", value = "买家姓名"),
            @ApiImplicitParam(name = "buyerPhone", value = "买家电话"),
            @ApiImplicitParam(name = "buyerAddress", value = "买家地址"),
            @ApiImplicitParam(name = "buyerOpenid", value = "买家微信openid"),
            @ApiImplicitParam(name = "orderAmount", value = "订单总金额"),
            @ApiImplicitParam(name = "orderStatus", value = "订单状态 0-新订单 1-已完结 2-已取消 默认0"),
            @ApiImplicitParam(name = "payStatus", value = "支付状态 0-待支付 1-已支付 默认0"),
            @ApiImplicitParam(name = "createTime", value = "创建时间"),
            @ApiImplicitParam(name = "updateTime", value = "更新时间"),
            @ApiImplicitParam(name = "status", value = "是否有效"),
            @ApiImplicitParam(name = "orderByCase", value = "排序", defaultValue = "create_time"),
            @ApiImplicitParam(name = "desc", value = "排序方式", defaultValue = "1"),
            @ApiImplicitParam(name = "pageNow", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "10")
    })
    public ResultUtil selectOrder(String orderId, String buyerName, String buyerPhone, String buyerAddress, String buyerOpenid, BigDecimal orderAmount, Integer orderStatus, Integer payStatus, Boolean status, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize) {
        if (null != orderByCase) {
            if (desc == null) {
                desc = false;
            }
            orderByCase += desc ? " desc" : " asc";
        } else {
            orderByCase = "create_time";
            if (desc == null) {
                desc = false;
            }
            orderByCase += desc ? " desc" : " asc";
        }
        return orderMasterService.selectOrder(orderId, buyerName, buyerPhone, buyerAddress, buyerOpenid, orderAmount, orderStatus, payStatus, status, orderByCase, desc, pageNow, pageSize);
    }
}
