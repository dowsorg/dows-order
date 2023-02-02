package org.dows.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderInstanceTenantOpVo extends OrderInstanceTenantVo{

    //退菜 赠菜 破损通用
    private String more;
    @ApiModelProperty(value = "退菜 赠菜 破损 通用名称")
    private String food;
    @ApiModelProperty(value = "退菜 赠菜 破损 通用数量")
    private Integer num;
    @ApiModelProperty(value = "退菜 赠菜 破损 通用原因")
    private String remarks;
    @ApiModelProperty(value = "退菜 赠菜 破损 通用金额")
    private BigDecimal reAmount;
    @ApiModelProperty(value = "退菜 赠菜 破损 通用时间")
    private Date dateTime;
    @ApiModelProperty(value = "退菜 赠菜 破损 通用操作人")
    private String operator;

}
