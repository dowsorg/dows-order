package org.dows.order.form;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderChangeTableNoForm {

    @ApiModelProperty(value = "订单号")
    private String orderNo;


    @ApiModelProperty(value = "换台编号")
    private String tableNo;

}
