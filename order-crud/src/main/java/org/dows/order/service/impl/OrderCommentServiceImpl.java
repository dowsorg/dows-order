package org.dows.order.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.order.mapper.OrderCommentMapper;
import org.dows.order.entity.OrderComment;
import org.dows.order.service.OrderCommentService;
import org.springframework.stereotype.Service;


/**
 * 订单-评价(OrderComment)表服务实现类
 *
 * @author lait
 * @since 2023-01-02 14:20:44
 */
@Service("orderCommentService")
public class OrderCommentServiceImpl extends MybatisCrudServiceImpl<OrderCommentMapper, OrderComment> implements OrderCommentService {

}

