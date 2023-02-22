package org.dows.order.rest.tenant;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.order.OrderCommentBiz;
import org.dows.order.form.OrderCommentPcForm;
import org.dows.order.vo.OrderCommentPcVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
@RequestMapping("tenant/orderComment")
public class TenantOrderCommentRest {

    private final OrderCommentBiz orderCommentBiz;



    /**
     * pc 查询PC订单评价
     * @param orderCommentPcForm
     * @return
     */
    @PostMapping("/getCommentListPage")
    @ApiOperation("查询PC订单评价")
    public Response<IPage<OrderCommentPcVo>> getCommentListPage(@Valid @RequestBody OrderCommentPcForm orderCommentPcForm){
        return Response.ok(orderCommentBiz.getCommentListPage(orderCommentPcForm));
    }





}

