package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderCommentResponseBo implements Serializable {


    private static final long serialVersionUID = -4540688243494302571L;
    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "图片")
    private List<String> pic;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "分钟前")
    private String min;
    @ApiModelProperty(value = "评论分数")
    private Integer score;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "点赞数")
    private Integer point;

}
