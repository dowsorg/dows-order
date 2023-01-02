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
@TableName(value = "orderitem")
@ApiModel(value = "OrderItem 对象", description = "订单项")
public class OrderItemEntity extends CrudEntity<OrderItemEntity>{

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("自增主键ID")
    @Length(19)
    @TableField(value = "id")
    private Long id;

    @ApiModelProperty("分布式ID")
    @Length(64)
    @TableField(value = "order_id")
    private String orderId;

    @ApiModelProperty("桌号")
    @Length(64)
    @TableField(value = "table_id")
    private String tableId;

    @ApiModelProperty("客户账号ID")
    @Length(64)
    @TableField(value = "account_id")
    private String accountId;

    @ApiModelProperty("商品ID")
    @Length(64)
    @TableField(value = "spu_id")
    private String spuId;

    @ApiModelProperty("商品名称")
    @Length(64)
    @TableField(value = "spu_name")
    private String spuName;

    @ApiModelProperty("订单项标记(0:有效，1：失效)")
    @Length(11)
    @TableField(value = "flag")
    private Long flag;

    @ApiModelProperty("数量")
    @Length()
    @TableField(value = "quantity")
    private BigDecimal quantity;

    @ApiModelProperty("单价")
    @Length()
    @TableField(value = "price")
    private BigDecimal price;

    @ApiModelProperty("时间戳")
    @Length()
    @TableField(value = "dt")
    private Date dt;


}
