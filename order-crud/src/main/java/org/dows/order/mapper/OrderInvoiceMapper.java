package org.dows.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.order.entity.OrderInvoice;

/**
 * 订单-发票(OrderInvoice)表数据库访问层
 *
 * @author lait
 * @since 2023-01-02 14:20:44
 */
@Mapper
public interface OrderInvoiceMapper extends MybatisCrudMapper<OrderInvoice> {

}

