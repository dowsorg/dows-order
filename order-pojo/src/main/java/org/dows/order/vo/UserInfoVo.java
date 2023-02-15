package org.dows.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class UserInfoVo {

    @ApiModelProperty(value = "头像")
    private String headUrl;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "生日")
    private String birthday;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "注册时间")
    private String createDate;
    @ApiModelProperty(value = "总订单量")
    private Integer orderNum;
    @ApiModelProperty(value = "消费总额")
    private BigDecimal amount;
    @ApiModelProperty(value = "最近下单")
    private String dateOf;
}
