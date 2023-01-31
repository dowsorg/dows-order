package org.dows.order.rest.tenant;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.order.api.OrderInstanceBizApiService;
import org.dows.order.form.OrderInstanceTenantForm;
import org.dows.order.service.OrderInstanceService;
import org.dows.order.vo.OrderInstanceTenantOpVo;
import org.dows.order.vo.OrderInstanceTenantVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "订单")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("tenant/order")
public class TenantOrderInstanceRest {


    private final OrderInstanceService orderInstanceService;

    private final OrderInstanceBizApiService bizApiService;


    /**
     * 后台订单分页列表
     * @param
     * @return
     */
    @PostMapping("/queryOrderInfo")
    @ApiOperation("后台订单和退款分页列表")
    public Response<IPage<OrderInstanceTenantVo>> queryOrderInfo(@RequestBody OrderInstanceTenantForm tenantForm){
        tenantForm.setOrderOpType(null);
        return Response.ok(bizApiService.selectOrderInstancePage(tenantForm));
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
        return Response.ok(bizApiService.selectOrderInstanceRePage(tenantForm));
    }

}
