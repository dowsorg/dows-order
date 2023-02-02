package org.dows.order.api;

import org.dows.order.bo.OrderCartAddBo;
import org.dows.order.bo.OrderCartQueryBo;
import org.dows.order.form.OrderCartQueryForm;
import org.dows.order.vo.OrderCartInfoVo;
import org.dows.order.vo.OrderCartTotalVo;

import java.util.List;

public interface OrderCartApiService {
    /**
     * 加入购物车
     * @return
     */
    void addOrderCart(OrderCartAddBo orderCartAddBo);

    /**
     * 清空购物车
     * @return
     */
    boolean cleanUpOrderCart(OrderCartQueryBo queryBo);

    /**
     * 查询购物车商品信息
     * @param queryBo
     * @return
     */
    OrderCartTotalVo getOrderCartInfo(OrderCartQueryBo queryBo);
}
