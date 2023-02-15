package org.dows.order.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OrderOutCommentForm {

    @NotNull
    @ApiModelProperty(value = "订单评价id")
    private Long commentId;

    @NotBlank
    @ApiModelProperty(value = "内容")
    private String content;
}
