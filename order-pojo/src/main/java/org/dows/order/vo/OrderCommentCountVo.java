package org.dows.order.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderCommentCountVo {

    @ApiModelProperty(value = "全部")
    private Integer allCount;

    @ApiModelProperty(value = "好评")
    private Integer niceCount;

    @ApiModelProperty(value = "差评")
    private Integer diffCount;

    @ApiModelProperty(value = "有图有视频")
    private Integer picCount;

    @ApiModelProperty(value = "回头客评价")
    private Integer againCount;
}
