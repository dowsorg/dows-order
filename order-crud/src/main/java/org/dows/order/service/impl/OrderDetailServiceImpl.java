package org.dows.order.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.order.mapper.OrderDetailMapper;
import org.dows.order.entity.OrderDetail;
import org.dows.order.service.OrderDetailService;
import org.springframework.stereotype.Service;


/**
 * 订单详情(OrderDetail)表服务实现类
 *
 * @author lait
 * @since 2023-01-02 14:20:44
 */
@Service("orderDetailService")
public class OrderDetailServiceImpl extends MybatisCrudServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}

