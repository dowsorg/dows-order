package org.dows.order.api;

import org.dows.order.bo.OrderCommentQueryBo;
import org.dows.order.form.OrderCommentForm;
import org.dows.order.form.OrderOutCommentForm;
import org.dows.order.vo.OrderCommentCountVo;
import org.dows.order.vo.OrderCommentResponseVo;

import java.util.List;

public interface OrderCommentApiSerivce {
    /**
     * 创建客户订单评价
     * @param orderCommentForm
     * @return
     */
    boolean createComment(OrderCommentForm orderCommentForm);

    /**
     * 商家评价
     * @param outCommentForm
     * @return
     */
    boolean outCreateComment(OrderOutCommentForm outCommentForm);

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
