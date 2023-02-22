package org.dows.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderMyCommentVo {

    @ApiModelProperty(value = "评价id")
    private Long orderCommentId;

    @ApiModelProperty(value = "门面照")
    private String profile;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "店铺地址")
    private String storeAddress;

    @ApiModelProperty(value = "评论分数")
    private Integer score;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "商家回复内容")
    private String returnContent;
}
