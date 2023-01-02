package org.dows.order.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
    
    private Long id;
    @ApiModelProperty("分布式ID")
    
    private String orderId;
    @ApiModelProperty("桌号")
    
    private String tableId;
    @ApiModelProperty("客户账号ID")
    
    private String accountId;
    @ApiModelProperty("商品ID")
    
    private String spuId;
    @ApiModelProperty("商品名称")
    
    private String spuName;
    @ApiModelProperty("订单项标记(0:有效，1：失效)")
    
    private Long flag;
    @ApiModelProperty("数量")
    
    private BigDecimal quantity;
    @ApiModelProperty("单价")
    
    private BigDecimal price;
    @JsonIgnore
    
    private Date dt;

}
