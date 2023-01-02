package org.dows.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.order.entity.OrderDetail;

/**
 * 订单详情(OrderDetail)表数据库访问层
 *
 * @author lait
 * @since 2023-01-02 14:20:44
 */
@Mapper
public interface OrderDetailMapper extends MybatisCrudMapper<OrderDetail> {

}

