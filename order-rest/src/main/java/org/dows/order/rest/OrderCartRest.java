package org.dows.order.rest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.order.OrderCatBiz;
import org.dows.order.bo.OrderCartAddBo;
import org.dows.order.bo.OrderCartQueryBo;
import org.dows.order.entity.OrderCart;
import org.dows.order.form.OrderCartAddForm;
import org.dows.order.form.OrderCartForm;
import org.dows.order.form.OrderCartQueryForm;
import org.dows.order.service.OrderCartService;
import org.dows.order.vo.OrderCartInfoVo;
import org.dows.utils.AssertUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
* 订单-预购单(OrderCart)表控制层
*
* @author 
* @date 
*/
@Api(tags = "订单-预购单")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orderCart")
public class OrderCartRest implements MybatisCrudRest<OrderCartForm, OrderCart, OrderCartService> {

    private final OrderCatBiz orderCatBiz;

    /**
     * 商品加入购物车
     * @param orderCartAddForm
     * @return
     */
    @PostMapping("/addOrderCart")
    @ApiOperation("商品加入购物车")
    public Response addOrderCart(@Valid @RequestBody OrderCartAddForm orderCartAddForm){
        OrderCartAddBo cartAddBo = BeanUtil.copyProperties(orderCartAddForm, OrderCartAddBo.class);
        if(StrUtil.isBlank(orderCartAddForm.getTableId())){ //不是收银台选购
            cartAddBo.setAccountId(null); //TODO
        }
        orderCatBiz.addOrderCart(cartAddBo);
        return Response.ok();
    }

    /**
     * 获取购物车信息
     * @param cartQueryForm
     * @return
     */
    @PostMapping("/getOrderCartInfo")
    @ApiOperation("获取购物车信息")
    public Response<List<OrderCartInfoVo>> getOrderCartInfo(@Valid @RequestBody OrderCartQueryForm cartQueryForm){
        OrderCartQueryBo queryBo = BeanUtil.copyProperties(cartQueryForm, OrderCartQueryBo.class);
        if(StrUtil.isBlank(queryBo.getTableId())){ //个人
            //TODO
            queryBo.setAccountId(null);
        }
        return Response.ok(orderCatBiz.getOrderCartInfo(queryBo));
    }

}

