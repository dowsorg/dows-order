package org.dows.order.entity;

import java.util.Date;

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

/**
 * 订单-评价(OrderComment)实体类
 *
 * @author lait
 * @since 2023-01-02 14:20:43
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "OrderComment对象", description = "订单-评价")
public class OrderComment implements CrudEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("单号")
    private String orderNo;

    @ApiModelProperty("主体（卖方|门店）账号ID")
    private String princalAccountId;

    @ApiModelProperty("主体（卖方|门店|seller）账号名称")
    private String princalAccountName;

    @ApiModelProperty("买方（buyer）账号ID")
    private String fromAccountId;

    @ApiModelProperty("买方（buyer）账号名称")
    private String fromAccountName;

    @ApiModelProperty("图片列表")
    private String pics;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("应用ID")
    private String appId;

    @ApiModelProperty("店铺ID")
    private String storeId;

    @ApiModelProperty("点赞数")
    private Integer point;

    @ApiModelProperty("评论分数")
    private Integer score;

    @ApiModelProperty("是否是商家回评")
    private Boolean fromMerchant;

    @ApiModelProperty("评价时间")
    private Date commentTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("时间戳")
    private Date dt;
    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("是否删除")
    private Boolean deleted;
}

