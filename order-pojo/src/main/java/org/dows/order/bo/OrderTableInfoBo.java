package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderTableInfoBo implements Serializable {
    private static final long serialVersionUID = 6778207954976631277L;

    @ApiModelProperty(value = "桌号")
    private String tableNo;

    @ApiModelProperty(value = "人数")
    private Integer peoples;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "耗时分钟")
    private Integer minute;

    @ApiModelProperty(value = "已上菜数量")
    private Integer finish;

    @ApiModelProperty(value = "总数量")
    private Integer total;

    @ApiModelProperty(value = "桌台状态 1:上菜中 2:菜上齐 3:已超时 4:已结账")
    private Integer tableStatus;
}
