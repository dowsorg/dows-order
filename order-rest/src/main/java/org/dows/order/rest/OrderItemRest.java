package org.dows.order.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.order.entity.OrderItem;
import org.dows.order.form.OrderItemForm;
import org.dows.order.service.OrderItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 订单项(orderItem)表控制层
*
* @author 
* @date 
*/
@Api(tags = "订单项")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("orderItem")
public class OrderItemRest implements MybatisCrudRest<OrderItemForm, OrderItem, OrderItemService> {


}

