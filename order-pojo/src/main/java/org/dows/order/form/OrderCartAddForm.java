package org.dows.order.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.naming.InsufficientResourcesException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * @author liuhonger
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "OrderCart 表单对象", description ="订单-预购单")
public class OrderCartAddForm implements Serializable {
    private static final long serialVersionUID = -5325470651808508305L;

    @NotBlank(message = "商品id不能为空!")
    @ApiModelProperty(value = "商品id")
    private String goodsSpuId;

    @NotBlank(message = "店铺id不能为空!")
    @ApiModelProperty(value = "店铺id")
    private String storeId;

    @ApiModelProperty(value = "桌号id")
    private String tableId;

    @NotNull(message = "是否是收银台选购不能为空!")
    @ApiModelProperty(value = "是否是收银台选购 1:是 0:否")
    private Integer cashier;

    @NotNull(message = "是否是加还是减不能为空!")
    @ApiModelProperty(value = "是否是加还是减 加:1 减:0")
    private Integer isAdd;

}
