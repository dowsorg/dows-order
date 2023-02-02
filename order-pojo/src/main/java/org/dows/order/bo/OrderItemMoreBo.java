package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderItemMoreBo implements Serializable {
    private static final long serialVersionUID = -3125691632771476681L;

    @ApiModelProperty(value = "赠送退菜份数")
    private Integer foodNum;

    @ApiModelProperty(value = "报损费用")
    private BigDecimal lossReporting;

    @ApiModelProperty(value = "备注原因")
    private String remarks;

    @ApiModelProperty(value = "操作时间")
    private Date opt;

    @ApiModelProperty(value = "操作人")
    private String userName;
}
