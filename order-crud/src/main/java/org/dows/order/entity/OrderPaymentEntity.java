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
@TableName(value = "orderpayment")
@ApiModel(value = "OrderPayment 对象", description = "订单-支付记录")
public class OrderPaymentEntity extends CrudEntity<OrderPaymentEntity>{

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    @Length(19)
    @TableField(value = "id")
    private Long id;

    @ApiModelProperty("订单单号")
    @Length(64)
    @TableField(value = "order_id")
    private String orderId;

    @ApiModelProperty("账号ID")
    @Length(64)
    @TableField(value = "account_id")
    private String accountId;

    @ApiModelProperty("账号名")
    @Length(64)
    @TableField(value = "account_name")
    private String accountName;

    @ApiModelProperty("支付金额")
    @Length()
    @TableField(value = "amount")
    private BigDecimal amount;

    @ApiModelProperty("支付流水号")
    @Length(64)
    @TableField(value = "pay_seqno")
    private String paySeqno;

    @ApiModelProperty("支付方式")
    @Length(11)
    @TableField(value = "pay_channel")
    private Long payChannel;

    @ApiModelProperty("支付状态")
    @Length(11)
    @TableField(value = "pay_state")
    private Long payState;

    @ApiModelProperty("时间戳")
    @Length()
    @TableField(value = "dt")
    private Date dt;


}
