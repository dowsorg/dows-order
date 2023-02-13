package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderCommentResponseBo implements Serializable{

    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "图片")
    private List<String> pic;
    @ApiModelProperty(value = "用户名头像")
    private String headUrl;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "分钟前")
    private String min;
    @ApiModelProperty(value = "评论分数")
    private Integer score;
    @ApiModelProperty(value = "内容")
    private String content;


}
