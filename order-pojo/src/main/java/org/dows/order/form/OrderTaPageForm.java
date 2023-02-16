package org.dows.order.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OrderTaPageForm {

    @NotNull
    @ApiModelProperty(value = "类型 0:堂食 1:自营外卖 2:打包")
    private Integer type;

    @ApiModelProperty(value = "订单id 分页用的")
    private Long orderId;

    @NotBlank
    @ApiModelProperty(value = "店铺id")
    private String storeId;

    //@JsonIgnore
    private String accountId;
}
