package org.dows.order.form;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "我的订单评价", description = "我的订单评价")
public class OrderMyCommentForm {


    @ApiModelProperty(value = "账号id")
    private String accountId;

    @ApiModelProperty(value = "评价id 分页用")
    private Long orderCommentId;
}
