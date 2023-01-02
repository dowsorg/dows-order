package org.dows.order.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.order.mapper.OrderCartMapper;
import org.dows.order.entity.OrderCart;
import org.dows.order.service.OrderCartService;
import org.springframework.stereotype.Service;


/**
 * 订单-预购单(OrderCart)表服务实现类
 *
 * @author lait
 * @since 2023-01-02 14:20:43
 */
@Service("orderCartService")
public class OrderCartServiceImpl extends MybatisCrudServiceImpl<OrderCartMapper, OrderCart> implements OrderCartService {

}

