package org.dows.order.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderTableTotalVo {

    @ApiModelProperty(value = "桌号/编号")
    private String tableNo;

    @ApiModelProperty(value = "今日翻台次数")
    private Integer todayNum;

    @ApiModelProperty(value = "上菜分钟")
    private String menuMin;

    @ApiModelProperty(value = "人数")
    private Integer peoples;

    @ApiModelProperty(value = "状态 1:上菜中 2:菜上齐 3:已超时 4:已结账")
    private Integer status;

    @ApiModelProperty(value = "订单详情")
    private List<OrderTableInfoVo> tableInfoList;
}
