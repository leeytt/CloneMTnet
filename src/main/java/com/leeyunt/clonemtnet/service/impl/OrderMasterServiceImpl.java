package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.dao.OrderMasterDao;
import com.leeyunt.clonemtnet.entity.OrderDetail;
import com.leeyunt.clonemtnet.entity.OrderMaster;
import com.leeyunt.clonemtnet.entity.dto.OrderDto;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import com.leeyunt.clonemtnet.service.OrderDetailService;
import com.leeyunt.clonemtnet.service.OrderMasterService;
import com.leeyunt.clonemtnet.utils.Page;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单主表 服务实现类
 * </p>
 *
 * @author leeyunts
 * @since 2020-04-10
 */
@Service
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterDao, OrderMaster> implements OrderMasterService {
    @Resource
    private OrderMasterDao orderMasterDao;

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public ResultUtil selectOrder(String orderId, String buyerName, String buyerPhone, String buyerAddress, String buyerOpenid, BigDecimal orderAmount, Integer orderStatus, Integer payStatus, Boolean status, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        if (null != orderId)
            map.put("orderId", orderId);
        if (null != buyerName)
            map.put("buyerName", buyerName);
        if (null != buyerPhone)
            map.put("buyerPhone", buyerPhone);
        if (null != buyerAddress)
            map.put("buyerAddress", buyerAddress);
        if (null != buyerOpenid)
            map.put("buyerOpenid", buyerOpenid);
        if (null != orderAmount)
            map.put("orderAmount", orderAmount);
        if (null != orderStatus)
            map.put("orderStatus", orderStatus);
        if (null != payStatus)
            map.put("payStatus", payStatus);
        if (null != status)
            map.put("status", status);
        if (null != orderByCase)
            map.put("orderByCase", orderByCase);
        if (null != pageNow && null != pageSize) {
            map.put("startPos", (pageNow - 1) * pageSize);
            map.put("pageSize", pageSize);
        }
        try {
            long count = orderMasterDao.getCount(map);
            if (count ==0 ) {
                return ResultUtil.ofFailMsg("未查询到结果");
            }
            List<OrderDto> list = orderMasterDao.dynamicSelect(map);
            HashMap<String, Object> data = new HashMap<>();
            if (null != list) {
                for (OrderDto orderDto: list) {
                    QueryWrapper<OrderDetail> wp = new QueryWrapper<>();
                    wp.eq("order_id", orderDto.getOrderId());
                    List<OrderDetail> orderDetailList = orderDetailService.list(wp);
                    if (null != orderDetailList) {
                        orderDto.setOrderDetailList(orderDetailList);
                    }
                }
            }
            pageSize = null != pageSize ? pageSize : (int) (count);
            pageNow = null != pageNow ? pageNow : 1;
            if (pageSize != 0) {
                Page page = new Page(count, pageNow);
                page.setPageSize(pageSize);
                data.put("page", page);
            }
            if (list.size() > 0) {
                data.put("list", list);
                return ResultUtil.ofSuccess(data);
            } else {
                return ResultUtil.ofStatusCode(StatusEnum.FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ofFailMsg("dynamic查询Order出错！");
        }
    }
}
