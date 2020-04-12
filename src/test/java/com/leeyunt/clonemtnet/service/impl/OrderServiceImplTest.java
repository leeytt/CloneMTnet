package com.leeyunt.clonemtnet.service.impl;

import com.leeyunt.clonemtnet.entity.OrderDetail;
import com.leeyunt.clonemtnet.entity.dto.OrderDto;
import com.leeyunt.clonemtnet.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID = "110110";

    @Test
    void create() {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName("李蕴涛");
        orderDto.setBuyerPhone("152223505812");
        orderDto.setBuyerAddress("重庆渝北区");
        orderDto.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId(1);
        o1.setProductQuantity(2);
        orderDetailList.add(o1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId(2);
        o2.setProductQuantity(1);
        orderDetailList.add(o2);

        orderDto.setOrderDetailList(orderDetailList);

        OrderDto result = orderService.create(orderDto);
        log.info("【创建订单】result={}", result);
    }
}