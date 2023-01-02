package org.dows.order.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.order.entity.OrderInvoice;
import org.dows.order.form.OrderInvoiceForm;
import org.dows.order.service.OrderInvoiceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 订单-发票(OrderInvoice)表控制层
*
* @author 
* @date 
*/
@Api(tags = "订单-发票")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("orderInvoice")
public class OrderInvoiceRest implements MybatisCrudRest<OrderInvoiceForm, OrderInvoice, OrderInvoiceService> {


}

