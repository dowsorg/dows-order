package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderInstancePaymentBo implements Serializable {
    private static final long serialVersionUID = 2948067579956915644L;

    @ApiModelProperty(value = "订单id")
    private String orderId;

}
