package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class OrderApplyRefundBo implements Serializable {

    private static final long serialVersionUID = -3052096392339840122L;
    @ApiModelProperty(value = "订单id")
    private Long orderId;
    @ApiModelProperty(value = "0:拒绝退款 1:退款")
    private Integer type;
    @ApiModelProperty(value = "操作申请退款备注")
    private String applyRemark;
}
