package org.dows.order.rest.tenant;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.order.OrderItemBiz;
import org.dows.order.bo.OrderItemFlagBo;
import org.dows.order.entity.OrderItem;
import org.dows.order.form.OrderItemFlagForm;
import org.dows.order.form.OrderItemForm;
import org.dows.order.service.OrderItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
* 订单项(orderItem)表控制层
*
* @author 
* @date 
*/
@Api(tags = "订单项")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orderItem")
public class TenantOrderItemRest {

    private final OrderItemBiz orderItemBiz;

    /**
     * 桌台化菜操作更多
     * @param flagForm
     * @return
     */
    @PostMapping("/updateOrderItem")
    @ApiOperation("桌台化菜操作更多")
    public Response<Boolean> updateOrderItem(@Valid @RequestBody OrderItemFlagForm flagForm){
        OrderItemFlagBo flagBo = BeanUtil.copyProperties(flagForm, OrderItemFlagBo.class);
        return Response.ok(orderItemBiz.updateOrderItem(flagBo));
    }

}

