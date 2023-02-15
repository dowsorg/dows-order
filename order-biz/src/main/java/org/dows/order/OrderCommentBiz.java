package org.dows.order;


import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.dows.account.api.AccountUserApi;
import org.dows.account.vo.AccountVo;
import org.dows.order.api.OrderCommentApiSerivce;
import org.dows.order.bo.OrderCommentQueryBo;
import org.dows.order.entity.OrderComment;
import org.dows.order.form.OrderCommentForm;
import org.dows.order.form.OrderOutCommentForm;
import org.dows.order.mapper.OrderCommentMapper;
import org.dows.order.service.OrderCommentService;
import org.dows.order.vo.OrderCommentCountVo;
import org.dows.order.vo.OrderCommentResponseVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCommentBiz implements OrderCommentApiSerivce {


    private final OrderCommentService orderCommentService;

    private final AccountUserApi accountUserApi;

    private final OrderCommentMapper commentMapper;

    @Override
    public boolean createComment(OrderCommentForm commentForm) {
        OrderComment comment = new OrderComment();
        AccountVo accountVo = accountUserApi.getInfoByAccountId(commentForm.getAccountId());
        comment.setOrderNo(commentForm.getOrderNo());
        comment.setFromAccountId(accountVo.getAccountId());
        comment.setFromAccountName(accountVo.getAccountName());
        comment.setPics(commentForm.getPics().stream().collect(Collectors.joining(",")));
        comment.setContent(commentForm.getContent());
        comment.setAppId(accountVo.getAppId());
        comment.setStoreId(commentForm.getStoreId());
        comment.setScore(commentForm.getScore());
        comment.setFromMerchant(false);
        comment.setCommentTime(DateUtil.date());
        comment.setDt(DateUtil.date());
        return orderCommentService.save(comment);
    }

    @Override
    public boolean outCreateComment(OrderOutCommentForm outCommentForm) {
        OrderComment comment = new OrderComment();
        OrderComment orderComment = commentMapper.selectById(outCommentForm.getCommentId());
        AccountVo accountVo = accountUserApi.getInfoByAccountId(orderComment.getFromAccountId());
        comment.setOrderNo(orderComment.getOrderNo());
        comment.setFromAccountId(accountVo.getAccountId());
        comment.setFromAccountName(accountVo.getAccountName());
        comment.setContent(outCommentForm.getContent());
        comment.setAppId(accountVo.getAppId());
        comment.setStoreId(orderComment.getStoreId());
        comment.setFromMerchant(true);
        comment.setCommentTime(DateUtil.date());
        comment.setDt(DateUtil.date());
        return orderCommentService.save(comment);
    }

    @Override
    public List<OrderCommentResponseVo> getCommentList(OrderCommentQueryBo commentQueryBo) {
        List<OrderCommentResponseVo> responseBoList = Lists.newArrayList();
        List<OrderComment> orderCommentsList = orderCommentService.lambdaQuery()
                .eq(OrderComment::getStoreId, commentQueryBo.getStoreId())
                .eq(OrderComment::getFromMerchant, false)
                .gt(Integer.valueOf(2).equals(commentQueryBo.getType()),OrderComment::getScore,2)
                .le(Integer.valueOf(3).equals(commentQueryBo.getType()),OrderComment::getScore,2)
                .isNotNull(Integer.valueOf(4).equals(commentQueryBo.getType()),OrderComment::getPics)
                //.isNotNull(Integer.valueOf(5).equals(commentQueryBo.getType()),OrderComment::getPics)
                .gt(commentQueryBo.getCommentId() != null,OrderComment::getId,commentQueryBo.getCommentId()) //分页用
                .orderByDesc(OrderComment::getId).last("limit 10")
                .list();
        if(!CollUtil.isEmpty(orderCommentsList)){
            List<String> accountIds = CollStreamUtil.toList(orderCommentsList, OrderComment::getFromAccountId);
            List<AccountVo> accountVoList = accountUserApi.getInfoByAccountIds(accountIds.toArray(new String[accountIds.size()]));
            Map<String, AccountVo> accountVoMap = CollStreamUtil.toMap(accountVoList, AccountVo::getAccountId, Function.identity());
            List<OrderComment> outCommentList = orderCommentService.lambdaQuery().in(OrderComment::getFromAccountId, accountIds).eq(OrderComment::getFromMerchant, true).list();
            Map<String, OrderComment> outCommentMap = CollStreamUtil.toMap(outCommentList, OrderComment::getFromAccountId, Function.identity());
            for (OrderComment comment : orderCommentsList) {
                OrderCommentResponseVo responseVo = new OrderCommentResponseVo();
                responseVo.setOrderNo(comment.getOrderNo());
                responseVo.setPic(StrUtil.split(comment.getPics(),","));
                if(accountVoMap.containsKey(comment.getFromAccountId())){
                    AccountVo accountVo = accountVoMap.get(comment.getFromAccountId());
                    responseVo.setHeadUrl(accountVo.getAvatar());
                    responseVo.setUserName(accountVo.getAccountName());
                }
                if(outCommentMap.containsKey(comment.getFromAccountId())){
                    OrderComment outComment = outCommentMap.get(comment.getFromAccountId());
                    responseVo.setOutContent(outComment.getContent());
                    responseVo.setOutTime(outComment.getCommentTime());
                }
                responseVo.setMin(DateUtil.between(comment.getDt(),DateUtil.date(), DateUnit.MINUTE)+"分钟前");
                responseVo.setScore(comment.getScore());
                responseVo.setContent(comment.getContent());
                responseBoList.add(responseVo);
            }
        }
        return responseBoList;
    }

    @Override
    public OrderCommentCountVo getCommentCount(String storeId) {
        return commentMapper.getCommentCount(storeId);
    }
}
