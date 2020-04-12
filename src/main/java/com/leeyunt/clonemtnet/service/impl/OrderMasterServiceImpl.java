package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.dao.OrderMasterDao;
import com.leeyunt.clonemtnet.entity.OrderMaster;
import com.leeyunt.clonemtnet.service.OrderMasterService;
import org.springframework.stereotype.Service;

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
}
