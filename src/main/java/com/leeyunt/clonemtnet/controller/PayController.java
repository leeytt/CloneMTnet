package com.leeyunt.clonemtnet.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leeyunt.clonemtnet.entity.OrderMaster;
import com.leeyunt.clonemtnet.exception.BaseException;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import com.leeyunt.clonemtnet.service.OrderMasterService;
import com.leeyunt.clonemtnet.service.PayServive;
import com.lly835.bestpay.model.PayResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * <p>
 * 微信支付接口 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-19
 */

@Api(tags = "微信支付接口")
@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private PayServive payServive;

    /**
     * 统一下单
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map) {
        //1、查询订单
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_id", orderId);
        OrderMaster orderMaster = orderMasterService.getOne(wrapper);
        if(orderMaster == null) {
            throw new BaseException(StatusEnum.PRODUCT_NOT_EXIST);
        }
        //2、发起支付
        PayResponse payResponse = payServive.create(orderMaster);

        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);

        return new ModelAndView("pay/create", map);
    }

    /**
     * 异步通知
     * @return
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyDate) {
        payServive.notify(notifyDate);

        //通知支付成功，修改订单为已支付，返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
