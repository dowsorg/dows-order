package org.dows.order.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "OrderInstanceQueryForm 表单对象", description = "订单查询接口")
public class OrderInstanceQueryForm implements Serializable {
    private static final long serialVersionUID = -6048742616492949816L;

    @ApiModelProperty(value = "订单类型(全部就不传 0:堂食|1:外卖|2:打包)")
    private Integer type;

    @ApiModelProperty(value = "店铺id")
    private String storeId;

    @ApiModelProperty(value = "桌号")
    private String tableNo;

    @ApiModelProperty(value = "用户名")
    private String accountName;

    @ApiModelProperty(value = "手机号")
    private String phone;
}
