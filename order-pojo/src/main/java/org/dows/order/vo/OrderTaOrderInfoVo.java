package org.dows.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Ta订单", description ="Ta订单")
public class OrderTaOrderInfoVo {


    @ApiModelProperty(value = "堂食")
    private OrderTaTableVo taTableVo;

    @ApiModelProperty(value = "打包")
    private OrderTaPackVo taPackVo;

    @ApiModelProperty(value = "外卖")
    private OrderTaTakeOutVo taTakeOutVo;


}
