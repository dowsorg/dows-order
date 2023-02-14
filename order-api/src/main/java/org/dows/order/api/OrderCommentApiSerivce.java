package org.dows.order.api;

import org.dows.order.bo.CreateCommentBo;
import org.dows.order.bo.OrderCommentQueryBo;
import org.dows.order.vo.OrderCommentCountVo;
import org.dows.order.vo.OrderCommentResponseVo;

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
    List<OrderCommentResponseVo> getCommentList(OrderCommentQueryBo commentQueryBo);

    /**
     * 订单评价的统计
     * @return
     */
    OrderCommentCountVo getCommentCount(String storeId);
}
