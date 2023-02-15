package org.dows.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderTaVo {

    @ApiModelProperty(value = "订单量")
    private Integer orderCount;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderAmount;

    @ApiModelProperty(value = "消费次数")
    private Integer orderConsumeNum;
}
