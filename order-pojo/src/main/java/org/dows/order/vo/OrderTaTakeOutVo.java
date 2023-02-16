package org.dows.order.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "Ta外卖订单", description ="Ta外卖订单")
public class OrderTaTakeOutVo{

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "创建时间")
    private Date dt;

    @ApiModelProperty(value = "菜品数量")
    private Integer menuNum;

    @ApiModelProperty(value = "外卖渠道 1:自营 2:饿了么 3:美团")
    private Integer takeOut;

    @ApiModelProperty(value = "支付渠道 1:微信 2:支付宝 3:储蓄卡 4:现金")
    private Integer payChannel;

    @ApiModelProperty(value = "金额")
    private BigDecimal amountTotal;


}
