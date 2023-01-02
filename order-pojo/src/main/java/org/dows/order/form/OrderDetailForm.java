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
@ApiModel(value = "OrderDetail 表单对象", description = "订单详情")
public class OrderDetailForm implements Serializable{
    @JsonIgnore
    @Length(19)
    private Long id;
    @ApiModelProperty("分布式ID")
    @Length(64)
    private String orderId;
    @ApiModelProperty("桌号")
    @Length(64)
    private String tableId;
    @ApiModelProperty("商品ID")
    @Length(64)
    private String spuId;
    @ApiModelProperty("商品规格ID")
    @Length(64)
    private String skuId;

}
