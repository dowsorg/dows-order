package org.dows.order.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.order.entity.OrderCart;
import org.dows.order.form.OrderCartForm;
import org.dows.order.service.OrderCartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



    @RequestMapping("/test")
    public void rest(){
        System.out.println("sdf");

    }

}

