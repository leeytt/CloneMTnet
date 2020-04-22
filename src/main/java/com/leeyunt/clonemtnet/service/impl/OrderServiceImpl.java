package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.leeyunt.clonemtnet.dao.ProductInfoDao;
import com.leeyunt.clonemtnet.entity.OrderDetail;
import com.leeyunt.clonemtnet.entity.OrderMaster;
import com.leeyunt.clonemtnet.entity.ProductInfo;
import com.leeyunt.clonemtnet.entity.dto.CartDto;
import com.leeyunt.clonemtnet.entity.dto.OrderDto;
import com.leeyunt.clonemtnet.exception.BaseException;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import com.leeyunt.clonemtnet.service.*;
import com.leeyunt.clonemtnet.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author leeyunts
 * @since 2020-04-10
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private PayServive payServive;

    @Resource
    private ProductInfoDao productInfoDao;

    /**
     * 取消订单
     */
    @Override
    @Transactional
    public void cancel(String buyerOpenid, String orderId) {
        QueryWrapper<OrderMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_openid", buyerOpenid);
        wrapper.eq("order_id", orderId);
        OrderMaster orderMaster = orderMasterService.getOne(wrapper);
        QueryWrapper<OrderDetail> wp = new QueryWrapper<>();
        wp.eq("order_id", orderId);
        List<OrderDetail> orderDetailList = orderDetailService.list(wp);

        //1. 判断订单状态(新订单才能取消)
        if (!orderMaster.getOrderStatus().equals(0)) {
            throw new BaseException(StatusEnum.ORDER_STATUS_ERROR);
        }

        //2. 修改订单状态
        UpdateWrapper<OrderMaster> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("order_status", 2);
        updateWrapper.eq("order_id", orderId);
        boolean result = orderMasterService.update(orderMaster, updateWrapper);
        if (!result) {
            throw new BaseException(StatusEnum.ORDER_UPDATE_ERROR);
        }

        //3. 返还库存
        if(CollectionUtils.isEmpty(orderDetailList)) {
            throw new BaseException(StatusEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDto> cartDtoList = new ArrayList<>();//购物车对象
        for (OrderDetail orderDetail:orderDetailList) {
            CartDto cartDto = new CartDto(orderDetail.getProductId(), orderDetail.getProductQuantity());
            cartDtoList.add(cartDto);
        }
        productInfoService.increaseStock(cartDtoList);

        //4. 如果已经支付，需要退款
        if (orderMaster.getPayStatus().equals(1)) {
            payServive.refund(orderMaster);
        }
    }

    /**
     * 完结订单
     */
    @Override
    @Transactional
    public void finish(String buyerOpenid, String orderId) {
        QueryWrapper<OrderMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_openid", buyerOpenid);
        wrapper.eq("order_id", orderId);
        OrderMaster orderMaster = orderMasterService.getOne(wrapper);
        QueryWrapper<OrderDetail> wp = new QueryWrapper<>();
        wp.eq("order_id", orderId);
        List<OrderDetail> orderDetailList = orderDetailService.list(wp);

        //1. 判断订单状态(新订单才能完结)
        if (!orderMaster.getOrderStatus().equals(0)) {
            throw new BaseException(StatusEnum.ORDER_STATUS_ERROR);
        }

        //2. 修改订单状态
        UpdateWrapper<OrderMaster> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("order_status", 1);
        updateWrapper.eq("order_id", orderId);
        boolean result = orderMasterService.update(orderMaster, updateWrapper);
        if (!result) {
            throw new BaseException(StatusEnum.ORDER_UPDATE_ERROR);
        }
    }

    /**
     * 支付订单
     */
    @Override
    @Transactional
    public void paid(String buyerOpenid, String orderId) {
        QueryWrapper<OrderMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_openid", buyerOpenid);
        wrapper.eq("order_id", orderId);
        OrderMaster orderMaster = orderMasterService.getOne(wrapper);
        QueryWrapper<OrderDetail> wp = new QueryWrapper<>();
        wp.eq("order_id", orderId);
        List<OrderDetail> orderDetailList = orderDetailService.list(wp);

        //判断订单状态
        if (!orderMaster.getOrderStatus().equals(0)) {
            throw new BaseException(StatusEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if (!orderMaster.getPayStatus().equals(0)) {
            throw new BaseException(StatusEnum.ORDER_PAY_STATUS_EMPTY);
        }

        //修改支付状态
        UpdateWrapper<OrderMaster> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("pay_status", 1);
        updateWrapper.eq("order_id", orderId);
        boolean result = orderMasterService.update(orderMaster, updateWrapper);
        if (!result) {
            throw new BaseException(StatusEnum.ORDER_PAID_ERROR);
        }
    }

    /**
     * 查询订单详情
     */
    @Override
    public OrderDto detail(String buyerOpenid, String orderId) {
        //1. 根据openid和orderId查找主订单
        QueryWrapper<OrderMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_openid", buyerOpenid);
        wrapper.eq("order_id", orderId);
        OrderMaster orderMaster = orderMasterService.getOne(wrapper);

        //2. 根据orderId查询订单详情
        QueryWrapper<OrderDetail> wp = new QueryWrapper<>();
        wp.eq("order_id", orderId);
        List<OrderDetail> orderDetailList = orderDetailService.list(wp);

        //3. 数据拼装
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);//属性拷贝
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    /**
     * 查询订单列表
     */
    @Override
    public List<OrderMaster> list(String buyerOpenid, Integer page, Integer size) {
        //根据openid查找list
        QueryWrapper<OrderMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_openid", buyerOpenid);
        List<OrderMaster> orderMasterList = orderMasterService.list(wrapper);
        return orderMasterList;
    }

    /**
     * 创建订单
     */
    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {

        String orderId = KeyUtil.genUniqueKey();//订单Id
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);//订单总金额

        List<CartDto> cartDtoList = new ArrayList<>();//购物车对象

        //1. 查询商品(数量、价格等)
        for (OrderDetail orderDetail:orderDto.getOrderDetailList()) {
            ProductInfo productInfo = productInfoDao.findById(orderDetail.getProductId());
            if (productInfo == null) {
                log.error("【创建订单】商品不存在，productId={}", orderDetail.getProductId());
                throw new BaseException(StatusEnum.PRODUCT_NOT_EXIST);
            }
            //2. 计算订单总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);//各个商品总价累加

            //订单详情入库
            BeanUtils.copyProperties(productInfo, orderDetail);//属性拷贝
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailService.save(orderDetail);

            //构建一个购物车对象 用于加减库存
            CartDto cartDto = new CartDto(orderDetail.getProductId(), orderDetail.getProductQuantity());
            cartDtoList.add(cartDto);
        }

        //3. 订单写入数据库（order_master和order_detail）
        orderDto.setOrderId(orderId);
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);//订单属性拷贝
        orderMaster.setOrderAmount(orderAmount);
        orderMasterService.save(orderMaster);

        //4. 减库存
        productInfoService.decreaseStock(cartDtoList);

        return orderDto;
    }
}
