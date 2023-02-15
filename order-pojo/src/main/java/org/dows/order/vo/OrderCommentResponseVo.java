package org.dows.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OrderCommentResponseVo implements Serializable {
    private static final long serialVersionUID = 2596334767711746105L;

    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "图片")
    private List<String> pic;
    @ApiModelProperty(value = "头像")
    private String headUrl;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "分钟前")
    private String min;
    @ApiModelProperty(value = "评论分数")
    private Integer score;
    @ApiModelProperty(value = "内容")
    private String content;

//    @ApiModelProperty(value = "商家回复内容")
//    private String outContent;
//    @ApiModelProperty(value = "商家回复时间")
//    private Date outTime;



}
