package org.dows.order.form;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OrderTaTypeForm {

    @NotBlank
    @ApiModelProperty(value = "店铺id")
    private String storeId;

    //@JsonIgnore
    private String accountId;
}
