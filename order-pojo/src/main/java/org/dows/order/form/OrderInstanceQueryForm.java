package org.dows.order.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "OrderInstanceQueryForm 表单对象", description = "订单查询接口")
public class OrderInstanceQueryForm implements Serializable {
    private static final long serialVersionUID = -6048742616492949816L;

    @ApiModelProperty(value = "订单类型(全部就不传 0:堂食|1:自营外卖|2:打包|3:申请退款)")
    private Integer type;

    @NotBlank(message = "店铺id不能空!")
    @ApiModelProperty(value = "店铺id")
    private String storeId;

    @ApiModelProperty(value = "桌号")
    private String tableNo;

    @ApiModelProperty(value = "电话/下单用户")
    private String searchVal;

    @ApiModelProperty(value = "时间")
    private Date date;
}
