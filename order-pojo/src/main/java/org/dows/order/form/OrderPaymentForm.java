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
@ApiModel(value = "OrderPayment 表单对象", description = "订单-支付记录")
public class OrderPaymentForm implements Serializable{
    @JsonIgnore
    private Long id;
    @ApiModelProperty("订单单号")
    
    private String orderId;
    @ApiModelProperty("账号ID")
    
    private String accountId;
    @ApiModelProperty("账号名")
    
    private String accountName;
    @ApiModelProperty("支付金额")
    
    private BigDecimal amount;
    @ApiModelProperty("支付流水号")
    
    private String paySeqno;
    @ApiModelProperty("支付方式")
   
    private Long payChannel;
    @ApiModelProperty("支付状态")
   
    private Long payState;
    @JsonIgnore
    
    private Date dt;

}
