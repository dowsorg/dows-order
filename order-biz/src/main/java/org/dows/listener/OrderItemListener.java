package org.dows.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.cglib.CglibUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.dows.log.api.BinlogListener;
import org.dows.log.api.annotation.Binlog;
import org.dows.order.bo.OrderItemMoreBo;
import org.dows.order.entity.OperateLog;
import org.dows.order.entity.OrderInstance;
import org.dows.order.entity.OrderItem;
import org.dows.order.service.OrderInstanceService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Binlog(hostName = "sh-cdb-1qjrazc8.sql.tencentcdb.com", database = "dxz",table = "order_item",tableSchemaClass = OrderItem.class)
public class OrderItemListener implements BinlogListener<OrderItem> {

    private final OrderInstanceService orderInstanceService;

    @Override
    public void onUpdate(OrderItem from, OrderItem to) {

        OperateLog log = new OperateLog();
        OrderItemMoreBo moreBo = JSONUtil.toBean(to.getMore(),OrderItemMoreBo.class);
        OrderInstance orderInstance = orderInstanceService.getById(from.getOrderId());
        log.setType(2);
        log.setStoreId(orderInstance.getStoreId());
        log.setOperator(moreBo.getUserId());
        log.setOperatorTime(moreBo.getOpt());
        log.setTableNo(orderInstance.getTableNo());
        log.setOrderNo(orderInstance.getOrderNo());
        log.setReason(moreBo.getRemarks());
        log.setFoodName(to.getSpuName());
        log.setDt(DateUtil.date());
        log.insert();
    }

    @Override
    public void onInsert(OrderItem data) {
        System.out.println("sdf");
    }

    @Override
    public void onDelete(OrderItem data) {

    }
}
