package org.dows.order.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class OrderRefundForm implements Serializable {
    private static final long serialVersionUID = 8043655148378201471L;

    @NotNull(message = "订单id不能为空!")
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "退款备注")
    private String remark;

    @ApiModelProperty(value = "图片")
    private List<String> images;
}
