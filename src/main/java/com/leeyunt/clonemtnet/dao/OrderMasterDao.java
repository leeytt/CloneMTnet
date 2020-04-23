package com.leeyunt.clonemtnet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leeyunt.clonemtnet.entity.OrderMaster;
import com.leeyunt.clonemtnet.entity.dto.OrderDto;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单主表 Mapper 接口
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-10
 */
public interface OrderMasterDao extends BaseMapper<OrderMaster> {
    long getCount(Map<String, Object> map);

    List<OrderDto> dynamicSelect(Map<String, Object> map);

    OrderMaster findById(Integer id);

    int dynamicUpdate(OrderMaster orderMaster);

}
