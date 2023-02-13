package org.dows.order.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class OperationLogVo {

    @ApiModelProperty(value = "操作时间")
    private Date dt;

    @ApiModelProperty(value = "操作备注")
    private String remark;

    @ApiModelProperty(value = "操作人")
    private String operator;


}
