package org.dows.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.order.entity.OrderCart;

/**
 * 订单-预购单(OrderCart)表数据库访问层
 *
 * @author lait
 * @since 2023-01-02 14:20:43
 */
@Mapper
public interface OrderCartMapper extends MybatisCrudMapper<OrderCart> {

}

