package org.dows.order.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.order.entity.OrderDetail;
import org.dows.order.form.OrderDetailForm;
import org.dows.order.service.OrderDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 订单详情(orderDetail)表控制层
*
* @author 
* @date 
*/
@Api(tags = "订单详情")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("orderDetail")
public class OrderDetailRest implements MybatisCrudRest<OrderDetailForm, OrderDetail, OrderDetailService> {


}

