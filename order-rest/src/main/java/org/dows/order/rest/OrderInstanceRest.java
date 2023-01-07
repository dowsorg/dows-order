package org.dows.order.rest;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.order.OrderInstanceBiz;
import org.dows.order.bo.OrderInstanceCreateBo;
import org.dows.order.bo.OrderInstanceQueryBo;
import org.dows.order.bo.OrderItemFlagBo;
import org.dows.order.entity.OrderInstance;
import org.dows.order.form.OrderInstanceCreateForm;
import org.dows.order.form.OrderInstanceForm;
import org.dows.order.form.OrderInstanceQueryForm;
import org.dows.order.form.OrderItemFlagForm;
import org.dows.order.service.OrderInstanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
* 订单(orderInstance)表控制层
*
* @author 
* @date 
*/
@Api(tags = "订单")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("orderInstance")
public class OrderInstanceRest implements MybatisCrudRest<OrderInstanceForm, OrderInstance, OrderInstanceService> {
    private final OrderInstanceBiz orderInstanceBiz;

    /**
     * 创建订单
     * @param createForm
     * @return
     */
    @PostMapping("/createOrderInstance")
    @ApiOperation("创建订单")
    public Response createOrderInstance(@Valid @RequestBody OrderInstanceCreateForm createForm){
        OrderInstanceCreateBo cartAddBo = BeanUtil.copyProperties(createForm, OrderInstanceCreateBo.class);
        if(createForm.getOperationType().equals(2)){ //店员下单
            cartAddBo.setAccountId(null);
        }else{
            // TODO
            cartAddBo.setAccountId(null);
        }
        orderInstanceBiz.creatOrderInstance(cartAddBo);
        return Response.ok();
    }


    /**
     * 查询订单详情
     * @param queryForm
     * @return
     */
    @PostMapping("/queryOrderInfo")
    @ApiOperation("查询订单详情")
    public Response queryOrderInfo(@Valid @RequestBody OrderInstanceQueryForm queryForm){
        OrderInstanceQueryBo queryBo = BeanUtil.copyProperties(queryForm, OrderInstanceQueryBo.class);
        return Response.ok(orderInstanceBiz.queryOrderInfo(queryBo));
    }


    /**
     * 桌台化菜操作更多
     * @param flagForm
     * @return
     */
    @PostMapping("/updateOrderItem")
    @ApiOperation("桌台化菜操作更多")
    public Response updateOrderItem(@Valid @RequestBody OrderItemFlagForm flagForm){
        OrderItemFlagBo flagBo = BeanUtil.copyProperties(flagForm, OrderItemFlagBo.class);
        return Response.ok(orderInstanceBiz.updateOrderItem(flagBo));
    }



}

