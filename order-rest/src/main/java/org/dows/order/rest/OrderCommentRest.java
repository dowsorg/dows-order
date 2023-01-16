package org.dows.order.rest;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.order.OrderCommentBiz;
import org.dows.order.bo.CreateCommentBo;
import org.dows.order.bo.OrderCommentQueryBo;
import org.dows.order.form.OrderCommentForm;
import org.dows.order.form.OrderCommentQueryForm;
import org.dows.order.vo.OrderCommentResponseVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
* 订单-评价(OrderComment)表控制层
*
* @author 
* @date 
*/
@Api(tags = "订单-评价")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orderComment")
public class OrderCommentRest {

    private final OrderCommentBiz orderCommentBiz;


    /**
     * 创建订单评价
     * @param commentForm
     * @return
     */
    @PostMapping("/createOrderComment")
    @ApiOperation("创建订单评价")
    public Response<Boolean> createOrderComment(@Valid @RequestBody OrderCommentForm commentForm){
        CreateCommentBo commentBo = BeanUtil.copyProperties(commentForm, CreateCommentBo.class);
        return Response.ok(orderCommentBiz.createComment(commentBo));
    }

    /**
     * 查询订单评价
     * @param commentForm
     * @return
     */
    @PostMapping("/queryOrderComment")
    @ApiOperation("创建订单评价")
    public Response<List<OrderCommentResponseVo>> queryOrderComment(@Valid @RequestBody OrderCommentQueryForm commentForm){
        OrderCommentQueryBo commentBo = BeanUtil.copyProperties(commentForm, OrderCommentQueryBo.class);
        return Response.ok(BeanUtil.copyToList(orderCommentBiz.getCommentList(commentBo),OrderCommentResponseVo.class));
    }



}

