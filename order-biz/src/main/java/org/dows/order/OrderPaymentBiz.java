package org.dows.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.order.api.OrderPaymentApiService;
import org.dows.order.bo.OrderPaymentCreatBo;
import org.dows.order.entity.OrderPayment;
import org.dows.order.service.OrderPaymentService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderPaymentBiz implements OrderPaymentApiService {

    private final OrderPaymentService orderPaymentService;

    @Override
    public void addOrderPayment(OrderPaymentCreatBo creatBo) {
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setOrderId(creatBo.getOrderId());
        orderPayment.setAccountId(creatBo.getAccountId());
        orderPayment.setAccountName("");
        orderPayment.setAmount(creatBo.getAmount());
        orderPayment.setPaySeqno(creatBo.getPaySeqno());
        orderPayment.setPayChannel(creatBo.getPayChannel());
        orderPayment.setPayState(creatBo.getPayState());
        orderPaymentService.save(orderPayment);
    }
}
