package org.dows.order.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.order.entity.OrderInstance;
import org.dows.order.form.OrderInstanceForm;
import org.dows.order.service.OrderInstanceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class OrderInstanceRest implements MybatisCrudRest<OrderInstanceForm, OrderInstance, OrderInstanceService> {



}

