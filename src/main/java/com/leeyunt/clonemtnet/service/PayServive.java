package com.leeyunt.clonemtnet.service;

import com.leeyunt.clonemtnet.entity.OrderMaster;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * <p>
 * 支付 服务类
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-19
 */
public interface PayServive {
    PayResponse create(OrderMaster orderMaster);

    PayResponse notify(String notifyDate);

    RefundResponse refund(OrderMaster orderMaster);
}
