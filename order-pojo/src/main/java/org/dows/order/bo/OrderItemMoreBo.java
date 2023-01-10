package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderItemMoreBo implements Serializable {
    private static final long serialVersionUID = -3125691632771476681L;

    //退菜 赠送 报损 加菜
    @ApiModelProperty(value = "值对象")
    private String key;

    @ApiModelProperty(value = "备注原因")
    private String val;

    @ApiModelProperty(value = "操作时间")
    private Date opt;

    @ApiModelProperty(value = "操作人")
    private String userName;
}
