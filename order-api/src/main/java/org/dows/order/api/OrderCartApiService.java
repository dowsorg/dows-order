package org.dows.order.api;

import org.dows.order.bo.OrderCartAddBo;
import org.dows.order.bo.OrderCartQueryBo;
import org.dows.order.vo.OrderCartInfoVo;

import java.util.List;

public interface OrderCartApiService {
    /**
     * 加入购物车
     * @return
     */
    void addOrderCart(OrderCartAddBo orderCartAddBo);

    /**
     * 查询购物车商品信息
     * @param queryBo
     * @return
     */
    List<OrderCartInfoVo> getOrderCartInfo(OrderCartQueryBo queryBo);
}
