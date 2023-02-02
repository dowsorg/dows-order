package org.dows.order.rest.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.order.OrderInstanceBiz;
import org.dows.order.bo.OrderInstanceCreateBo;
import org.dows.order.bo.OrderInstanceQueryBo;
import org.dows.order.form.OrderInstanceCreateForm;
import org.dows.order.form.OrderInstanceQueryForm;
import org.dows.order.vo.OrderInstanceInfoVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("orderInstance")
public class OrderInstanceRest {
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
    public Response<List<OrderInstanceInfoVo>> queryOrderInfo(@Valid @RequestBody OrderInstanceQueryForm queryForm){
        OrderInstanceQueryBo queryBo = BeanUtil.copyProperties(queryForm, OrderInstanceQueryBo.class);
        if(StrUtil.isBlank(queryBo.getTableNo())){
            queryBo.setAccountId(null);// TODO
        }
        return Response.ok(orderInstanceBiz.queryOrderInfo(queryBo));
    }







}

