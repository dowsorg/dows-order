package org.dows.order;


import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.order.api.OrderCommentApiSerivce;
import org.dows.order.bo.CreateCommentBo;
import org.dows.order.bo.OrderCommentQueryBo;
import org.dows.order.bo.OrderCommentResponseBo;
import org.dows.order.entity.OrderComment;
import org.dows.order.service.OrderCommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCommentBiz implements OrderCommentApiSerivce {


    private final OrderCommentService orderCommentService;

    @Override
    public boolean createComment(CreateCommentBo commentBo) {
        OrderComment comment = new OrderComment();
        comment.setOrderNo(commentBo.getOrderId());
        comment.setPrincalAccountId(null);
        comment.setPrincalAccountName(null);
        comment.setFromAccountId(null);
        comment.setFromAccountName(null);
        comment.setPics(commentBo.getPics().stream().collect(Collectors.joining(",")));
        comment.setContent(commentBo.getContent());
        comment.setAppId(null);
        comment.setStoreId(commentBo.getStoreId());
        comment.setPoint(0);
        comment.setScore(commentBo.getScore());
        comment.setFromMerchant(Integer.valueOf(1).equals(commentBo.getFromMerchant()));
        comment.setCommentTime(DateUtil.date());
        comment.setDt(DateUtil.date());
        return orderCommentService.save(comment);
    }

    @Override
    public List<OrderCommentResponseBo> getCommentList(OrderCommentQueryBo commentQueryBo) {
        return null;
    }
}
