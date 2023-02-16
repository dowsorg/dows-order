package org.dows.order.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderTaAllVo {

    @ApiModelProperty(value = "堂食订单列表")
    private List<OrderTaTableVo> tableVoList;

    @ApiModelProperty(value = "打包订单列表")
    private List<OrderTaPackVo> packVoList;

    @ApiModelProperty(value = "外卖订单列表")
    private List<OrderTaTakeOutVo> taTakeOutList;
}
