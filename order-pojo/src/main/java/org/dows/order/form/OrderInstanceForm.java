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
@ApiModel(value = "OrderInstance 表单对象", description = "订单")
public class OrderInstanceForm implements Serializable{
    @JsonIgnore
    @Length(19)
    private Long id;
    @ApiModelProperty("分布式ID")
    @Length(64)
    private String orderId;
    @ApiModelProperty("桌号唯一标识")
    @Length(64)
    private String tableId;
    @ApiModelProperty("客户账号ID")
    @Length(64)
    private String accountId;
    @ApiModelProperty("店铺ID")
    @Length(64)
    private String storeId;
    @ApiModelProperty("应用ID")
    @Length(64)
    private String appId;
    @ApiModelProperty("租户号")
    @Length(64)
    private String tenantId;
    @ApiModelProperty("备注")
    @Length(64)
    private String remark;
    @ApiModelProperty("总金额")
    @Length()
    private BigDecimal amount;
    @ApiModelProperty("人数")
    @Length(11)
    private Long peoples;
    @ApiModelProperty("订单类型(0:堂食|1:外卖|2:打包)")
    @Length(11)
    private Long typ;
    @ApiModelProperty("订单状态(0:制作中|1:制作完成|2:待支付|3:已支付|4:进行中|5:已结束)")
    @Length(11)
    private Long status;
    @JsonIgnore
    @Length()
    private Date dt;

}
