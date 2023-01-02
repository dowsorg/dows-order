package org.dows.order.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
* @description 
*
* @author 
* @date 2023年1月2日 下午2:04:57
*/
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "OrderComment 表单对象", description = "订单-评价")
public class OrderCommentForm implements Serializable{
    @JsonIgnore
    @Length(19)
    private Long id;
    @ApiModelProperty("单号")
    @Length(64)
    private String orderNo;
    @ApiModelProperty("主体（卖方|门店）账号ID")
    @Length(64)
    private String princalAccountId;
    @ApiModelProperty("主体（卖方|门店|seller）账号名称")
    @Length(64)
    private String princalAccountName;
    @ApiModelProperty("买方（buyer）账号ID")
    @Length(64)
    private String fromAccountId;
    @ApiModelProperty("买方（buyer）账号名称")
    @Length(64)
    private String fromAccountName;
    @ApiModelProperty("图片列表")
    @Length(64)
    private String pics;
    @ApiModelProperty("内容")
    @Length(64)
    private String content;
    @ApiModelProperty("应用ID")
    @Length(64)
    private String appId;
    @ApiModelProperty("店铺ID")
    @Length(64)
    private String storeId;
    @ApiModelProperty("点赞数")
    @Length(11)
    private Long point;
    @ApiModelProperty("评论分数")
    @Length(11)
    private Long score;
    @ApiModelProperty("是否是商家回评")
    @Length()
    private Integer fromMerchant;
    @ApiModelProperty("评价时间")
    @Length()
    private Date commentTime;
    @JsonIgnore
    @Length()
    private Date dt;

}
