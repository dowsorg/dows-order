package org.dows.order.rest.tenant;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.order.api.OrderInstanceBizApiService;
import org.dows.order.bo.OrderApplyRefundBo;
import org.dows.order.bo.OrderInstanceCreateBo;
import org.dows.order.bo.OrderInstanceQueryBo;
import org.dows.order.form.OrderApplyRefundForm;
import org.dows.order.form.OrderInstanceCreateForm;
import org.dows.order.form.OrderInstanceQueryForm;
import org.dows.order.form.OrderInstanceTenantForm;
import org.dows.order.service.OrderInstanceService;
import org.dows.order.vo.OrderInstanceInfoVo;
import org.dows.order.vo.OrderInstanceTenantOpVo;
import org.dows.order.vo.OrderInstanceTenantVo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "订单")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tenant/order")
public class TenantOrderInstanceRest {


    private final OrderInstanceService orderInstanceService;

    private final OrderInstanceBizApiService orderInstanceBiz;




    /**
     * 后台订单分页列表
     * @param
     * @return
     */
    @PostMapping("/getOrderInfoList")
    @ApiOperation("后台订单和退款分页列表")
    public Response<IPage<OrderInstanceTenantVo>> queryOrderInfo(@RequestBody OrderInstanceTenantForm tenantForm){
        tenantForm.setOrderOpType(null);
        return Response.ok(orderInstanceBiz.selectOrderInstancePage(tenantForm));
    }


    /**
     * 后台订单赠送退菜列表
     * @param
     * @return
     */
    @PostMapping("/queryGiveOrderInfo")
    @ApiOperation("后台订单赠送退菜报损列表")
    public Response<IPage<OrderInstanceTenantOpVo>> queryGiveOrderInfo(@RequestBody OrderInstanceTenantForm tenantForm){
        tenantForm.setOrderRefund(null);
        return Response.ok(orderInstanceBiz.selectOrderInstanceRePage(tenantForm));
    }


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
    public Response<List<OrderInstanceInfoVo>> queryOrderInfo(@Valid @RequestBody OrderInstanceQueryForm queryForm){
        OrderInstanceQueryBo queryBo = BeanUtil.copyProperties(queryForm, OrderInstanceQueryBo.class);
        if(StrUtil.isBlank(queryBo.getTableNo())){
            queryBo.setAccountId(null);// TODO
        }
        return Response.ok(orderInstanceBiz.queryOrderInfo(queryBo));
    }

    /**
     * 申请退款操作
     * @param refundForm
     * @return
     */
    @PostMapping("/applyRefund")
    @ApiOperation("申请退款操作")
    public Response<Boolean> applyRefundOrder(@Valid @RequestBody OrderApplyRefundForm refundForm){
        return Response.ok(orderInstanceBiz.applyRefund(BeanUtil.toBean(refundForm, OrderApplyRefundBo.class)));
    }

    /**
     * 确认出餐
     * @param orderId
     * @return
     */
    @PostMapping("/diningOrder/{orderId}")
    @ApiOperation("确认出餐")
    public Response<Boolean> diningOrder(@PathVariable Long orderId){
        return Response.ok(orderInstanceBiz.diningOrder(orderId));
    }

}
