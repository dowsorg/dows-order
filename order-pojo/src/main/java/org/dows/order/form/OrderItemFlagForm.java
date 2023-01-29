package org.dows.order.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.dows.order.enums.OrderItemFlagEnum;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItemFlagForm implements Serializable {


    private static final long serialVersionUID = -4367622479667899083L;
    @ApiModelProperty(value = "订单项id")
    private Long orderItemId;

    @ApiModelProperty(value = "1:划菜 2:退菜 3:赠送 4:报损 5:加菜")
    private OrderItemFlagEnum flag;

    @ApiModelProperty(value = "报损金额")
    private BigDecimal reporting;

    @ApiModelProperty(value = "退菜份数")
    private Integer returning;

    @ApiModelProperty(value = "原因")
    private String remark;

}
