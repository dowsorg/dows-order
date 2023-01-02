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
@TableName(value = "orderdetail")
@ApiModel(value = "OrderDetail 对象", description = "订单详情")
public class OrderDetailEntity extends CrudEntity<OrderDetailEntity>{

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

    @ApiModelProperty("商品ID")
    @Length(64)
    @TableField(value = "spu_id")
    private String spuId;

    @ApiModelProperty("商品规格ID")
    @Length(64)
    @TableField(value = "sku_id")
    private String skuId;


}
