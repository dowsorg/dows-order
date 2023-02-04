package org.dows.order.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class OrderApplyRefundForm implements Serializable {

    private static final long serialVersionUID = 4429906412060473926L;

    @ApiModelProperty(value = "订单id")
    @NotNull(message = "订单id不能为空!")
    private Long orderId;

    @ApiModelProperty(value = "操作类型 0:拒绝退款 1:退款")
    @NotNull(message = "操作类型不能为空!")
    private Integer type;

    @ApiModelProperty(value = "操作申请退款备注")
    private String applyRemark;
}
