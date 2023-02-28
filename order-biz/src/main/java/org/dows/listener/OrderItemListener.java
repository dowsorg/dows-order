package org.dows.listener;

import org.dows.log.api.BinlogListener;
import org.dows.log.api.annotation.Binlog;
import org.dows.order.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
@Binlog(hostName = "sh-cdb-1qjrazc8.sql.tencentcdb.com", database = "dxz",table = "order_item",tableSchemaClass = OrderItem.class)
public class OrderItemListener implements BinlogListener<OrderItem> {

    @Override
    public void onUpdate(OrderItem from, OrderItem to) {
        System.out.println("aa");

    }

    @Override
    public void onInsert(OrderItem data) {
        System.out.println("sdf");
    }

    @Override
    public void onDelete(OrderItem data) {

    }
}
