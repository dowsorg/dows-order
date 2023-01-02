package org.dows.order.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.order.mapper.OrderInvoiceMapper;
import org.dows.order.entity.OrderInvoice;
import org.dows.order.service.OrderInvoiceService;
import org.springframework.stereotype.Service;


/**
 * 订单-发票(OrderInvoice)表服务实现类
 *
 * @author lait
 * @since 2023-01-02 14:20:44
 */
@Service("orderInvoiceService")
public class OrderInvoiceServiceImpl extends MybatisCrudServiceImpl<OrderInvoiceMapper, OrderInvoice> implements OrderInvoiceService {

}

