package org.dows.order.rest.user;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.order.OrderCommentBiz;
import org.dows.order.bo.OrderCommentQueryBo;
import org.dows.order.form.OrderCommentForm;
import org.dows.order.form.OrderCommentQueryForm;
import org.dows.order.form.OrderOutCommentForm;
import org.dows.order.vo.OrderCommentCountVo;
import org.dows.order.vo.OrderCommentResponseVo;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("user/orderComment")
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
        //TODO 登录时调
        commentForm.setAccountId("1610967398052057089");
        return Response.ok(orderCommentBiz.createComment(commentForm));
    }

//    /**
//     * 创建商家订单评价
//     * @param commentForm
//     * @return
//     */
//    @PostMapping("/outCreateComment")
//    @ApiOperation("创建商家订单评价")
//    public Response<Boolean> createOrderComment(@Valid @RequestBody OrderOutCommentForm commentForm){
//        return Response.ok(orderCommentBiz.outCreateComment(commentForm));
//    }

    /**
     * 查询订单评价
     * @param commentForm
     * @return
     */
    @PostMapping("/queryOrderComment")
    @ApiOperation("查询订单评价")
    public Response<List<OrderCommentResponseVo>> queryOrderComment(@Valid @RequestBody OrderCommentQueryForm commentForm){
        OrderCommentQueryBo commentBo = BeanUtil.copyProperties(commentForm, OrderCommentQueryBo.class);
        return Response.ok(BeanUtil.copyToList(orderCommentBiz.getCommentList(commentBo),OrderCommentResponseVo.class));
    }


    /**
     * 订单评价统计
     * @param storeId
     * @return
     */
    @GetMapping("/getCount/{storeId}")
    @ApiOperation("订单评价统计")
    public Response<OrderCommentCountVo> getCount(@PathVariable String storeId){
        return Response.ok(orderCommentBiz.getCommentCount(storeId));
    }



}

