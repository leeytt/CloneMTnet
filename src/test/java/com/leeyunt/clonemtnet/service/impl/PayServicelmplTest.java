package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leeyunt.clonemtnet.entity.OrderMaster;
import com.leeyunt.clonemtnet.service.OrderMasterService;
import com.leeyunt.clonemtnet.service.PayServive;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class PayServicelmplTest {
    @Autowired
    private PayServive payServive;

    @Autowired
    private OrderMasterService orderMasterService;

    @Test
    void create() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_id", "1586585599098907553");
        OrderMaster orderMaster = orderMasterService.getOne(wrapper);
        payServive.create(orderMaster);
    }

    @Test
    void refund() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("order_id", "1587471175853575877");
        OrderMaster orderMaster = orderMasterService.getOne(wrapper);
        payServive.refund(orderMaster);
    }
}