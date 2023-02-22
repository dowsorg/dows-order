package org.dows.order;


import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.dows.account.api.AccountUserApi;
import org.dows.account.vo.AccountVo;
import org.dows.order.api.OrderCommentApiSerivce;
import org.dows.order.bo.OrderCommentQueryBo;
import org.dows.order.entity.OrderComment;
import org.dows.order.form.*;
import org.dows.order.mapper.OrderCommentMapper;
import org.dows.order.service.OrderCommentService;
import org.dows.order.vo.OrderCommentCountVo;
import org.dows.order.vo.OrderCommentPcVo;
import org.dows.order.vo.OrderCommentResponseVo;
import org.dows.order.vo.OrderMyCommentVo;
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

    //private final StoreInstanceApi storeInstanceApi;

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

    @Override
    public IPage<OrderCommentPcVo> getCommentListPage(OrderCommentPcForm commentPcForm) {
        String accountId = null;
        if(StrUtil.isBlank(commentPcForm.getAccountName())){
        }
        if(StrUtil.isBlank(commentPcForm.getAccountNo())){

        }
        LambdaQueryWrapper<OrderComment> queryWrapper = Wrappers.lambdaQuery(OrderComment.class)
                .eq(!StrUtil.isBlank(accountId), OrderComment::getFromAccountId, accountId)
                .eq(!StrUtil.isBlank(commentPcForm.getStoreId()), OrderComment::getStoreId, commentPcForm.getStoreId())
                .apply(commentPcForm.getStartDate() != null, "date_format(create_time,'%Y-%m-%d') >= {0}", DateUtil.formatDate(commentPcForm.getStartDate()))
                .apply(commentPcForm.getEndDate() != null, "date_format(create_time,'%Y-%m-%d') <= {0}", DateUtil.formatDate(commentPcForm.getEndDate()));
        IPage<OrderComment> paging = new Page(commentPcForm.getCurrent(),commentPcForm.getSize());
        IPage<OrderComment> commentIPage = orderCommentService.page(paging, queryWrapper);
        List<OrderCommentPcVo> commentPcVos = CollUtil.newArrayList();
        if(!CollUtil.isEmpty(commentIPage.getRecords())){
            List<String> accountIds = commentIPage.getRecords().stream().map(OrderComment::getFromAccountId).collect(Collectors.toList());
            List<AccountVo> accountVoList = accountUserApi.getInfoByAccountIds(accountIds.toArray(new String[accountIds.size()]));
            Map<String, AccountVo> accountVoMap = CollStreamUtil.toMap(accountVoList, AccountVo::getAccountId, Function.identity());
            List<OrderComment> outCommentList = orderCommentService.lambdaQuery().in(OrderComment::getFromAccountId, accountIds).eq(OrderComment::getFromMerchant, true).list();
            Map<String, String> outCommentMap = CollStreamUtil.toMap(outCommentList, OrderComment::getFromAccountId, OrderComment::getContent);
            for (OrderComment record : commentIPage.getRecords()) {
                OrderCommentPcVo pcVo = new OrderCommentPcVo();
                if(accountVoMap.containsKey(record.getFromAccountId())){
                    AccountVo accountVo =  accountVoMap.get(record.getFromAccountId());
                    pcVo.setHeadUrl(accountVo.getAvatar());
                    pcVo.setAccountName(accountVo.getAccountName());
                }
                pcVo.setPics(StrUtil.split(record.getPics(),","));
                pcVo.setDt(record.getDt());
                pcVo.setStoreName("五月天");
                pcVo.setContent(record.getContent());
                pcVo.setReturnContent(outCommentMap.get(record.getFromAccountId()));
                commentPcVos.add(pcVo);
            }
        }
        IPage<OrderCommentPcVo> returnComment = new Page<>();
        returnComment.setRecords(commentPcVos);
        returnComment.setTotal(commentIPage.getTotal());
        returnComment.setCurrent(commentIPage.getCurrent());
        return returnComment;
    }

    @Override
    public List<OrderMyCommentVo> getMyCommentList(OrderMyCommentForm myCommentForm) {
        List<OrderMyCommentVo> list = CollUtil.newArrayList();
        List<OrderComment> comments = orderCommentService.lambdaQuery()
                .eq(OrderComment::getFromAccountId, myCommentForm.getAccountId())
                .gt(myCommentForm.getOrderCommentId() != null,OrderComment::getId, myCommentForm.getOrderCommentId())
                .eq(OrderComment::getFromMerchant,false)
                .orderByDesc(OrderComment::getId).list();
        if(!CollUtil.isEmpty(comments)){
            List<String> accountIds = comments.stream().map(OrderComment::getFromAccountId).collect(Collectors.toList());
            List<OrderComment> outCommentList = orderCommentService.lambdaQuery().in(OrderComment::getFromAccountId, accountIds).eq(OrderComment::getFromMerchant, true).list();
            Map<String, String> outCommentMap = CollStreamUtil.toMap(outCommentList, OrderComment::getFromAccountId, OrderComment::getContent);

            for (OrderComment comment : comments) {
                OrderMyCommentVo commentVo = new OrderMyCommentVo();
                commentVo.setProfile("http://df");
                commentVo.setStoreName("五月天店");
                commentVo.setStoreAddress("徐家汇");
                commentVo.setScore(comment.getScore());
                commentVo.setContent(comment.getContent());
                commentVo.setReturnContent(outCommentMap.get(comment.getFromAccountId()));
                commentVo.setOrderCommentId(comment.getId());
                list.add(commentVo);
            }
        }
        return list;
    }
}
