package org.dows.order.rest.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.order.api.OrderInstanceBizApiService;
import org.dows.order.bo.OrderInstancePaymentBo;
import org.dows.order.bo.OrderInstanceQueryBo;
import org.dows.order.form.OrderInstanceCreateForm;
import org.dows.order.form.OrderInstanceQueryForm;
import org.dows.order.form.OrderMyForm;
import org.dows.order.form.OrderRefundForm;
import org.dows.order.vo.OrderInstanceDetailVo;
import org.dows.order.vo.OrderInstanceInfoVo;
import org.dows.order.vo.OrderMyInstanceInfoVo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
@RequestMapping("user/orderInstance")
public class OrderInstanceRest {
    private final OrderInstanceBizApiService orderInstanceBiz;

    /**
     * 创建订单 支付的
     * @param createForm
     * @return
     */
    @PostMapping("/createOrderInstance")
    @ApiOperation("创建订单")
    public Response createOrderInstance(@Valid @RequestBody OrderInstanceCreateForm createForm){
        OrderInstancePaymentBo cartAddBo = BeanUtil.copyProperties(createForm, OrderInstancePaymentBo.class);
        if(Integer.valueOf(1).equals(createForm.getOrderSource())){ //店员下单
            cartAddBo.setAccountId(null);
        }else{
            // TODO
            cartAddBo.setAccountId(null);
        }
        orderInstanceBiz.createOrderInstance(cartAddBo);
        return Response.ok();
    }

    


    /**
     * 用户申请退款
     * @param refundForm
     * @return
     */
    @PostMapping("/customerApplyRefund")
    @ApiOperation("用户申请退款(用户C端)")
    public Response<Boolean> customerApplyRefund(@Valid @RequestBody OrderRefundForm refundForm){
        return Response.ok(orderInstanceBiz.customerApplyRefund(refundForm));
    }


    /**
     * c 端用户的 订单详情
     * @param orderId
     * @return
     */
    @GetMapping("/getOrderDetailInfo/{orderId}")
    @ApiOperation("订单详情(用户C端)")
    public Response<OrderInstanceDetailVo> getOrderDetailInfo(@PathVariable Long orderId){
        return Response.ok(orderInstanceBiz.getOrderDetailInfo(orderId));
    }


    /**
     * c 端 我的订单
     * @param myForm
     * @return
     */
    @PostMapping("/getMyOrderInstance")
    @ApiOperation("我的订单(用户C端)")
    public Response<List<OrderMyInstanceInfoVo>> getMyOrderInstance(@Valid @RequestBody OrderMyForm myForm){
        return Response.ok(orderInstanceBiz.getMyOrderInstance(myForm));
    }




}

