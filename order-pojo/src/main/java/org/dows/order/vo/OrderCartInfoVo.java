package org.dows.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderCartInfoVo implements Serializable {
    private static final long serialVersionUID = -4458334407812318604L;

    @ApiModelProperty(value = "spuId")
    private String goodSpuId;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "商品图片")
    private String goodsPic;

    @ApiModelProperty(value = "商品名称")
    private String goodName;


}
