package org.dows.order.rest.tenant;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.order.api.OrderInstanceBizApiService;
import org.dows.order.form.OrderInstanceAdminForm;
import org.dows.order.service.OrderInstanceService;
import org.dows.order.vo.OrderInstanceAdminVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "订单")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/order")
public class OrderInstanceAdminRest {


    private final OrderInstanceService orderInstanceService;

    private final OrderInstanceBizApiService bizApiService;


    /**
     * 后台订单分页列表
     * @param
     * @return
     */
    @PostMapping("/queryOrderInfo")
    @ApiOperation("后台订单分页列表")
    public Response<IPage<OrderInstanceAdminVo>> queryOrderInfo(@RequestBody OrderInstanceAdminForm adminForm){
        return Response.ok(bizApiService.selectOrderInstancePage(adminForm));
    }


    /**
     * 后台订单赠送退菜列表
     * @param
     * @return
     */
    @PostMapping("/queryGiveOrderInfo")
    @ApiOperation("后台订单赠送退菜列表")
    public Response<IPage<OrderInstanceAdminVo>> queryGiveOrderInfo(@RequestBody OrderInstanceAdminForm adminForm){
        return Response.ok(null);
    }

}
