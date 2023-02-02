package org.dows.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderCartTotalVo implements Serializable {
    private static final long serialVersionUID = -3467433821715283979L;

    @ApiModelProperty(value = "购物车信息")
    private List<OrderCartInfoVo> list;

    @ApiModelProperty(value = "总价格")
    private BigDecimal total;
}
