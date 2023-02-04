package org.dows.order.entity;

import java.math.BigDecimal;
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
 * 订单项(OrderItem)实体类
 *
 * @author lait
 * @since 2023-01-02 14:20:45
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "OrderItem对象", description = "订单项")
public class OrderItem{

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("自增主键ID")
    private Long id;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("桌号")
    private String tableNo;

    @ApiModelProperty("客户账号ID")
    private String accountId;

    @ApiModelProperty("商品ID")
    private Long spuId;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("订单项标记(0:有效，1：失效)")
    private Integer flag;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("单价")
    private BigDecimal price;

    @ApiModelProperty("更多内容")
    private String more;

    @ApiModelProperty("备注")
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("时间戳")
    private Date dt;

//    @JsonIgnore
//    @TableLogic
//    @TableField(fill = FieldFill.INSERT)
//    @ApiModelProperty("是否删除")
//    private Boolean deleted;
}

