package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leeyunt.clonemtnet.entity.OrderMaster;
import com.leeyunt.clonemtnet.exception.BaseException;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import com.leeyunt.clonemtnet.service.OrderMasterService;
import com.leeyunt.clonemtnet.service.OrderService;
import com.leeyunt.clonemtnet.service.PayServive;
import com.leeyunt.clonemtnet.utils.JsonUtil;
import com.leeyunt.clonemtnet.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付 服务实现类
 * </p>
 *
 * @author leeyunts
 * @since 2020-04-19
 */
@Slf4j
@Service
public class PayServicelmpl implements PayServive {
    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMasterService orderMasterService;

    private static final String ORDER_NAME = "微信点餐订单";

    @Override
    public PayResponse create(OrderMaster orderMaster) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderMaster.getBuyerOpenid());
        payRequest.setOrderAmount(orderMaster.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderMaster.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付，payRequest={}", JsonUtil.toJson(payRequest));

        //发起支付
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付，payResponse={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyDate) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyDate);
        log.info("【微信支付】异步通知，payResponse={}", JsonUtil.toJson(payResponse));

        //查询订单
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_id", payResponse.getOrderId());
        OrderMaster orderMaster = orderMasterService.getOne(wrapper);

        //判断订单是否存在
        if (orderMaster == null) {
            log.error("【微信支付】异步通知，订单不存在，orderID={}", payResponse.getOrderId());
            throw new BaseException(StatusEnum.PRODUCT_NOT_EXIST);
        }

        //判断金额是否一致
        if (!MathUtil.equals(payResponse.getOrderAmount(), orderMaster.getOrderAmount().doubleValue())) {
            log.error("【微信支付】异步通知，订单金额不一致，orderId={}, 微信通知金额={}, 系统金额={}",
                    payResponse.getOrderId(),payResponse.getOrderAmount(),orderMaster.getOrderAmount());
            throw new BaseException(StatusEnum.ORDER_NOTIFY_MONEY_ERROR);
        }

        //修改订单支付状态
        orderService.paid(orderMaster.getBuyerOpenid(), orderMaster.getOrderId());

        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderMaster orderMaster) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderMaster.getOrderId());
        refundRequest.setOrderAmount(orderMaster.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】request={}", JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}", JsonUtil.toJson(refundResponse));

        return refundResponse;
    }
}
