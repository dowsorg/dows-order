package org.dows.order.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.order.mapper.OrderPaymentMapper;
import org.dows.order.entity.OrderPayment;
import org.dows.order.service.OrderPaymentService;
import org.springframework.stereotype.Service;


/**
 * 订单-支付记录(OrderPayment)表服务实现类
 *
 * @author lait
 * @since 2023-01-02 14:20:45
 */
@Service("orderPaymentService")
public class OrderPaymentServiceImpl extends MybatisCrudServiceImpl<OrderPaymentMapper, OrderPayment> implements OrderPaymentService {

}

