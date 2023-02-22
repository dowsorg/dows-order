package org.dows.order.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OrderCommentPcVo {

    @ApiModelProperty(value = "头像")
    private String headUrl;

    @ApiModelProperty(value = "账号名称")
    private String accountName;

    @ApiModelProperty(value = "评价时间")
    private Date dt;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "评价内容")
    private String content;

    @ApiModelProperty(value = "商家回复评价内容")
    private String returnContent;


}
