package org.dows.order.form;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OrderCommentPcForm extends QueryParam{

    @ApiModelProperty(value = "客户编号")
    private String accountNo;

    @ApiModelProperty(value = "客户昵称")
    private String accountName;

    @ApiModelProperty(value = "门店id")
    private String storeId;

    @ApiModelProperty(value = "评价开始时间")
    private Date startDate;

    @ApiModelProperty(value = "评价开始结束时间")
    private Date endDate;
}
