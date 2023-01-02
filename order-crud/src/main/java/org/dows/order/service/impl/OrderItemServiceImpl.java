package org.dows.order.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.order.mapper.OrderItemMapper;
import org.dows.order.entity.OrderItem;
import org.dows.order.service.OrderItemService;
import org.springframework.stereotype.Service;


/**
 * 订单项(OrderItem)表服务实现类
 *
 * @author lait
 * @since 2023-01-02 14:20:45
 */
@Service("orderItemService")
public class OrderItemServiceImpl extends MybatisCrudServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}

