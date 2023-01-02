package org.dows.order.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.order.entity.OrderPayment;
import org.dows.order.form.OrderPaymentForm;
import org.dows.order.service.OrderPaymentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 订单-支付记录(OrderPayment)表控制层
*
* @author 
* @date 
*/
@Api(tags = "订单-支付记录")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("orderPayment")
public class OrderPaymentRest implements MybatisCrudRest<OrderPaymentForm, OrderPayment, OrderPaymentService> {


}

