package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateCommentBo implements Serializable {
    private static final long serialVersionUID = -5958799987435628263L;

    @ApiModelProperty("订单号")
    private String orderId;
    @ApiModelProperty("图片列表")
    private String pics;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("店铺ID")
    private String storeId;
    @ApiModelProperty("评论分数 1:2:3:4:5")
    private Long score;
    @ApiModelProperty("是否是商家回评 0:不是 1:是")
    private Integer fromMerchant;

}
