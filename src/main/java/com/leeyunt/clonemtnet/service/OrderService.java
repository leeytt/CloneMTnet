package com.leeyunt.clonemtnet.service;

import com.leeyunt.clonemtnet.entity.OrderMaster;
import com.leeyunt.clonemtnet.entity.dto.OrderDto;

import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-10
 */
public interface OrderService {
    /**
     * 创建订单
     */
    OrderDto create(OrderDto orderDto);

    /**
     * 查询订单列表
     */
    List<OrderMaster> list(String buyerOpenid, Integer page, Integer size);

    /**
     * 查询订单详情
     */
    OrderDto detail(String buyerOpenid, String orderId);

    /**
     * 取消订单
     */
    void cancel(String buyerOpenid, String orderId);

    /**
     * 完结订单
     */
    void finish(String buyerOpenid, String orderId);

    /**
     * 支付订单
     */
    void paid(String buyerOpenid, String orderId);
}
