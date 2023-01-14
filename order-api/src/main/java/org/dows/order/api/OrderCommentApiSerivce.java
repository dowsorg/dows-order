package org.dows.order.api;

import org.dows.order.bo.CreateCommentBo;
import org.dows.order.bo.OrderCommentQueryBo;
import org.dows.order.bo.OrderCommentResponseBo;

import java.util.List;

public interface OrderCommentApiSerivce {
    /**
     * 创建订单评价
     * @param commentBo
     * @return
     */
    boolean createComment(CreateCommentBo commentBo);

    /**
     * 查询订单评价信息
     * @param commentQueryBo
     * @return
     */
    List<OrderCommentResponseBo> getCommentList(OrderCommentQueryBo commentQueryBo);
}
