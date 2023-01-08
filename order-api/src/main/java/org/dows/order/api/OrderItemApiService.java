package org.dows.order.api;

import org.dows.order.bo.OrderItemFlagBo;

public interface OrderItemApiService {

    /**
     * 修改订单项的更多 是否化菜(0:未上菜 1:已上菜 2:退菜 3:赠送 4:报损) 操作
     * @return
     */
    boolean updateOrderItem(OrderItemFlagBo flagBo);
}
