package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OrderCartQueryBo implements Serializable {
    private static final long serialVersionUID = 5154317941108702185L;

    @ApiModelProperty(value = "店铺id")
    private String storeId;

    @ApiModelProperty(value = "桌号id")
    private String tableId;

    @ApiModelProperty(value = "账号id")
    private String accountId;


}
