package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuhonger
 */
@Data
public class OrderCartAddBo implements Serializable {
    private static final long serialVersionUID = -5325470651808508305L;

    @ApiModelProperty(value = "商品id")
    private String goodsSpuId;

    @ApiModelProperty(value = "店铺id")
    private String storeId;

    @ApiModelProperty(value = "桌号")
    private String tableNo;

    @ApiModelProperty(value = "客户账号Id")
    private String accountId;

    @ApiModelProperty(value = "是否是加还是减 加:1 减:0")
    private Integer isAdd;


}
