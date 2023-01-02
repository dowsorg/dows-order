package org.dows.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.order.entity.OrderItem;

/**
 * 订单项(OrderItem)表数据库访问层
 *
 * @author lait
 * @since 2023-01-02 14:20:45
 */
@Mapper
public interface OrderItemMapper extends MybatisCrudMapper<OrderItem> {

}

