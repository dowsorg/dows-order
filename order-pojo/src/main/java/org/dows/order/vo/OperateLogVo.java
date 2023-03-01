package org.dows.order.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OperateLogVo {

    @ApiModelProperty(value = "门店")
    private String storeName;
    @ApiModelProperty(value = "操作人")
    private String operator;
    @ApiModelProperty(value = "操作时间")
    private Date operatorTime;
    @ApiModelProperty(value = "台号")
    private String tableNo;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "原因")
    private String reason;
    @ApiModelProperty(value = "菜品")
    private String foodName;
}
