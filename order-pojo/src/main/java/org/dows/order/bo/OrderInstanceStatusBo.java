package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderInstanceStatusBo implements Serializable {
    private static final long serialVersionUID = 822108569496162138L;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "支付渠道")
    private Integer payChannel;

    @ApiModelProperty(value = "订单状态(0:制作中|1:制作完成|2:待支付|3:已支付|4:进行中|5:已结束)")
    private Integer status;
}
