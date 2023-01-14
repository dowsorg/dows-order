package org.dows.order;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.order.api.OrderCommentApiSerivce;
import org.dows.order.bo.CreateCommentBo;
import org.dows.order.bo.OrderCommentQueryBo;
import org.dows.order.bo.OrderCommentResponseBo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCommentBiz implements OrderCommentApiSerivce {
    @Override
    public boolean createComment(CreateCommentBo commentBo) {
        return false;
    }

    @Override
    public List<OrderCommentResponseBo> getCommentList(OrderCommentQueryBo commentQueryBo) {
        return null;
    }
}
