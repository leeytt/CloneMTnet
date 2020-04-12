package com.leeyunt.clonemtnet.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leeyunt.clonemtnet.entity.OrderDetail;
import com.leeyunt.clonemtnet.entity.OrderMaster;
import com.leeyunt.clonemtnet.entity.dto.OrderDto;
import com.leeyunt.clonemtnet.exception.BaseException;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import com.leeyunt.clonemtnet.service.OrderService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 买家端订单 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-10
 */
@Slf4j
@Api(tags = "买家端订单")
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("create")
    @ApiOperation(value = "创建订单", notes = "创建订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(name = "address", value = "地址", required = true),
            @ApiImplicitParam(name = "openid", value = "买家微信openid", required = true),
            @ApiImplicitParam(name = "items", value = "购物车")})
    public ResultUtil create(String name, String phone, String address, String openid, String items) {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName(name);
        orderDto.setBuyerPhone(phone);
        orderDto.setBuyerAddress(address);
        orderDto.setBuyerOpenid(openid);
        List<OrderDetail> orderDetailList;
        try {
            //将json字符串转换成list
            Gson gson = new Gson();
            orderDetailList = gson.fromJson(items, new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e) {
            log.error("【创建订单】参数不正确，items={}", items);
            throw new BaseException(StatusEnum.PARAM_ERROR);
        }
        orderDto.setOrderDetailList(orderDetailList);
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new BaseException(StatusEnum.CART_EMPTY);
        }
        OrderDto dto =  orderService.create(orderDto);//创建订单
        Map<String,String> map = new HashMap<>();
        map.put("orderId", String.valueOf(dto.getOrderId()));
        return ResultUtil.of(0, "成功", map);
//        return ResultUtil.ofSuccess(dto);
    }

    /**
     * 查询订单列表
     */
    @GetMapping("list")
    @ApiOperation(value = "查询订单列表", notes = "查询订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "买家微信openid", required = true),
            @ApiImplicitParam(name = "page", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "分页大小", defaultValue = "10")})
    public ResultUtil list(String openid, Integer page, Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new BaseException(-1, "openid为空");
        }
        List<OrderMaster> orderMasterList= orderService.list(openid, page, size);
        return ResultUtil.of(0, "成功", orderMasterList);
//        return ResultUtil.ofSuccess(orderDtoList);
    }

    /**
     * 查询订单详情
     */
    @GetMapping("detail")
    @ApiOperation(value = "查询订单详情", notes = "查询订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "买家微信openid", required = true),
            @ApiImplicitParam(name = "orderId", value = "订单Id", required = true)})
    public ResultUtil detail(String openid, String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单详情】openid为空");
            throw new BaseException(-1, "openid为空");
        }
        if (StringUtils.isEmpty(orderId)) {
            log.error("【查询订单详情】orderId为空");
            throw new BaseException(-1, "orderId为空");
        }
        OrderDto orderDto = orderService.detail(openid, orderId);
        return ResultUtil.of(0, "成功", orderDto);
//        return ResultUtil.ofSuccess(orderDto);
    }


    /**
     * 取消订单
     */
    @PostMapping("cancel")
    @ApiOperation(value = "取消订单", notes = "取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "买家微信openid", required = true),
            @ApiImplicitParam(name = "orderId", value = "订单Id", required = true)})
    public ResultUtil cancel(String openid, String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单详情】openid为空");
            throw new BaseException(-1, "openid为空");
        }
        if (StringUtils.isEmpty(orderId)) {
            log.error("【查询订单详情】orderId为空");
            throw new BaseException(-1, "orderId为空");
        }
        orderService.cancel(openid, orderId);
        return ResultUtil.of(0, "成功",null);
    }

    /**
     * 完结订单
     */
    @PostMapping("finish")
    @ApiOperation(value = "完结订单", notes = "完结订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "买家微信openid", required = true),
            @ApiImplicitParam(name = "orderId", value = "订单Id", required = true)})
    public ResultUtil finish(String openid, String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单详情】openid为空");
            throw new BaseException(-1, "openid为空");
        }
        if (StringUtils.isEmpty(orderId)) {
            log.error("【查询订单详情】orderId为空");
            throw new BaseException(-1, "orderId为空");
        }
        orderService.finish(openid, orderId);
        return ResultUtil.of(0, "成功",null);
    }

    /**
     * 支付订单
     */
    @PostMapping("paid")
    @ApiOperation(value = "支付订单", notes = "支付订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "买家微信openid", required = true),
            @ApiImplicitParam(name = "orderId", value = "订单Id", required = true)})
    public ResultUtil paid(String openid, String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单详情】openid为空");
            throw new BaseException(-1, "openid为空");
        }
        if (StringUtils.isEmpty(orderId)) {
            log.error("【查询订单详情】orderId为空");
            throw new BaseException(-1, "orderId为空");
        }
        orderService.paid(openid, orderId);
        return ResultUtil.of(0, "成功",null);
    }
}
