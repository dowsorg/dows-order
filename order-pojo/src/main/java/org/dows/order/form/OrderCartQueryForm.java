package org.dows.order.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OrderCartQueryForm implements Serializable {
    private static final long serialVersionUID = 5154317941108702185L;

    @NotNull(message = "店铺id不能为空!")
    @ApiModelProperty(value = "店铺id")
    private String storeId;

    @ApiModelProperty(value = "桌号id")
    private String tableId;



}
