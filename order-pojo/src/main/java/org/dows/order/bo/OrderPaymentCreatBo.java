package org.dows.order.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderPaymentCreatBo implements Serializable {
    private static final long serialVersionUID = 6382377447044742861L;

    private String orderId;
    private String accountId;
    private BigDecimal amount;
    private String paySeqno;
    private Integer payChannel;
    private Integer payState;
}
