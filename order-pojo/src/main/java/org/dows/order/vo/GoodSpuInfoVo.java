package org.dows.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class GoodSpuInfoVo {

    @ApiModelProperty(value = "订单项id")
    private Long orderItemId;

    @ApiModelProperty(value = "商品信息")
    private String goodName;

    @ApiModelProperty(value = "数量")
    private Integer quantity;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "是否化菜(0:正常 1:划菜 2:退菜 3:赠送 4:报损)")
    private Integer flag;

    @ApiModelProperty(value = "退菜数量")
    private Integer refundNum;

    @ApiModelProperty(value = "备注")
    private String remark;
}
