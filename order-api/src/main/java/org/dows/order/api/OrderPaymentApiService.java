package org.dows.order.api;

import org.dows.order.bo.OrderPaymentCreatBo;

public interface OrderPaymentApiService {
    /**
     * 支付记录
     * @param creatBo
     */
    void addOrderPayment(OrderPaymentCreatBo creatBo);
}
