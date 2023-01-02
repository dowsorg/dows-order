package org.dows.order.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.order.entity.OrderComment;
import org.dows.order.form.OrderCommentForm;
import org.dows.order.service.OrderCommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 订单-评价(OrderComment)表控制层
*
* @author 
* @date 
*/
@Api(tags = "订单-评价")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("orderComment")
public class OrderCommentRest implements MybatisCrudRest<OrderCommentForm, OrderComment, OrderCommentService> {


}

