package org.dows.order.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.order.mapper.OrderInstanceMapper;
import org.dows.order.entity.OrderInstance;
import org.dows.order.service.OrderInstanceService;
import org.springframework.stereotype.Service;


/**
 * 订单(OrderInstance)表服务实现类
 *
 * @author lait
 * @since 2023-01-02 14:20:44
 */
@Service("orderInstanceService")
public class OrderInstanceServiceImpl extends MybatisCrudServiceImpl<OrderInstanceMapper, OrderInstance> implements OrderInstanceService {

}

