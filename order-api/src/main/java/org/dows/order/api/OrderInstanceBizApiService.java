package org.dows.order.api;

import org.dows.order.bo.*;
import org.dows.order.vo.OrderInstanceInfoVo;

import java.util.List;

public interface OrderInstanceBizApiService {

    /**
     * 创建订单
     * @param createBo
     */
    void creatOrderInstance(OrderInstanceCreateBo createBo);

    /**
     * 查询订单信息
     * @param queryBo
     * @return
     */
    List<OrderInstanceInfoVo> queryOrderInfo(OrderInstanceQueryBo queryBo);

    /**
     * 支付成功修改订单状态
     * @return
     */
    boolean updateOrderInstance(OrderInstanceStatusBo statusBo);

    /**
     * 修改订单项的更多 是否化菜(0:未上菜 1:已上菜 2:退菜 3:赠送 4:报损) 操作
     * @return
     */
    boolean updateOrderItem(OrderItemFlagBo flagBo);

    /**
     * 返回桌台的订单信息 催万乐用
     * @param storeId
     * @param tableIds
     * @return
     */
    List<OrderTableInfoBo> getOrderTableInfo(String storeId,List<String> tableIds);


}
