package org.dows.order.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OperateLogQueryForm extends QueryParam{

    @ApiModelProperty(value = "类型 1:开台 2：划菜 3：退菜 4:加菜 5：赠送 6：报损 7：账单合并 8：结账 9：退单 10：发券 11：商品 ")
    private Integer type;

    @ApiModelProperty(value = "门店id")
    private String storeId;

    @ApiModelProperty(value = "操作人")
    private String operatorId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "操作开始时间")
    private Date startDate;

    @ApiModelProperty(value = "操作结束时间")
    private Date endDate;

}
