package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItemFlagBo implements Serializable {
    private static final long serialVersionUID = -7460094284626843606L;

    @ApiModelProperty(value = "订单项id")
    private Long orderItemId;

    @ApiModelProperty(value = "0:划菜 1:已上菜 2:退菜 3:赠送 4:报损")
    private Integer flag;

    @ApiModelProperty(value = "报损金额")
    private BigDecimal reporting;

    @ApiModelProperty(value = "退菜份数")
    private Integer returning;

    @ApiModelProperty(value = "原因")
    private String remark;

}
