package org.dows.order.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OrderTaPageForm {

    @ApiModelProperty(value = "订单id 分页用的")
    private Long orderId;

    @NotBlank
    @ApiModelProperty(value = "店铺id")
    private String storeId;

    //@JsonIgnore
    private String accountId;
}
