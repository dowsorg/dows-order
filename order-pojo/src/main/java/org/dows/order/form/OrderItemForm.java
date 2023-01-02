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
@ApiModel(value = "OrderItem 表单对象", description = "订单项")
public class OrderItemForm implements Serializable{
    @JsonIgnore
    @Length(19)
    private Long id;
    @ApiModelProperty("分布式ID")
    @Length(64)
    private String orderId;
    @ApiModelProperty("桌号")
    @Length(64)
    private String tableId;
    @ApiModelProperty("客户账号ID")
    @Length(64)
    private String accountId;
    @ApiModelProperty("商品ID")
    @Length(64)
    private String spuId;
    @ApiModelProperty("商品名称")
    @Length(64)
    private String spuName;
    @ApiModelProperty("订单项标记(0:有效，1：失效)")
    @Length(11)
    private Long flag;
    @ApiModelProperty("数量")
    @Length()
    private BigDecimal quantity;
    @ApiModelProperty("单价")
    @Length()
    private BigDecimal price;
    @JsonIgnore
    @Length()
    private Date dt;

}
