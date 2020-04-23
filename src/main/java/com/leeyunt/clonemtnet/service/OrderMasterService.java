package com.leeyunt.clonemtnet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leeyunt.clonemtnet.entity.OrderMaster;
import com.leeyunt.clonemtnet.utils.ResultUtil;

import java.math.BigDecimal;

/**
 * <p>
 * 订单主表 服务类
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-10
 */
public interface OrderMasterService extends IService<OrderMaster> {
    ResultUtil selectOrder(String orderId, String buyerName, String buyerPhone, String buyerAddress, String buyerOpenid, BigDecimal orderAmount, Integer orderStatus, Integer payStatus, Boolean status, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize);
}
