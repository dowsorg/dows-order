package org.dows.order.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderTableNoVo {

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "账单总计")
    private BigDecimal amount;

    @ApiModelProperty(value = "订单详情")
    private List<GoodSpuInfoVo>  goodSpuInfoVoList;
}
