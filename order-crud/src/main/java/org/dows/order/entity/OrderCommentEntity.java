package org.dows.order.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.dows.framework.crud.mybatis.CrudEntity;

import java.util.Date;
import java.math.BigDecimal;

/**
* @description 
*
* @author 
* @date 
*/
@Data
@NoArgsConstructor
@TableName(value = "ordercomment")
@ApiModel(value = "OrderComment 对象", description = "订单-评价")
public class OrderCommentEntity extends CrudEntity<OrderCommentEntity>{

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    @Length(19)
    @TableField(value = "id")
    private Long id;

    @ApiModelProperty("单号")
    @Length(64)
    @TableField(value = "order_no")
    private String orderNo;

    @ApiModelProperty("主体（卖方|门店）账号ID")
    @Length(64)
    @TableField(value = "princal_account_id")
    private String princalAccountId;

    @ApiModelProperty("主体（卖方|门店|seller）账号名称")
    @Length(64)
    @TableField(value = "princal_account_name")
    private String princalAccountName;

    @ApiModelProperty("买方（buyer）账号ID")
    @Length(64)
    @TableField(value = "from_account_id")
    private String fromAccountId;

    @ApiModelProperty("买方（buyer）账号名称")
    @Length(64)
    @TableField(value = "from_account_name")
    private String fromAccountName;

    @ApiModelProperty("图片列表")
    @Length(64)
    @TableField(value = "pics")
    private String pics;

    @ApiModelProperty("内容")
    @Length(64)
    @TableField(value = "content")
    private String content;

    @ApiModelProperty("应用ID")
    @Length(64)
    @TableField(value = "app_id")
    private String appId;

    @ApiModelProperty("店铺ID")
    @Length(64)
    @TableField(value = "store_id")
    private String storeId;

    @ApiModelProperty("点赞数")
    @Length(11)
    @TableField(value = "point")
    private Long point;

    @ApiModelProperty("评论分数")
    @Length(11)
    @TableField(value = "score")
    private Long score;

    @ApiModelProperty("是否是商家回评")
    @Length()
    @TableField(value = "from_merchant")
    private Integer fromMerchant;

    @ApiModelProperty("评价时间")
    @Length()
    @TableField(value = "comment_time")
    private Date commentTime;

    @ApiModelProperty("时间戳")
    @Length()
    @TableField(value = "dt")
    private Date dt;


}
