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
@ApiModel(value = "OrderPayment 表单对象", description = "订单-支付记录")
public class OrderPaymentForm implements Serializable{
    @JsonIgnore
    @Length(19)
    private Long id;
    @ApiModelProperty("订单单号")
    @Length(64)
    private String orderId;
    @ApiModelProperty("账号ID")
    @Length(64)
    private String accountId;
    @ApiModelProperty("账号名")
    @Length(64)
    private String accountName;
    @ApiModelProperty("支付金额")
    @Length()
    private BigDecimal amount;
    @ApiModelProperty("支付流水号")
    @Length(64)
    private String paySeqno;
    @ApiModelProperty("支付方式")
    @Length(11)
    private Long payChannel;
    @ApiModelProperty("支付状态")
    @Length(11)
    private Long payState;
    @JsonIgnore
    @Length()
    private Date dt;

}
