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
@TableName(value = "orderinvoice")
@ApiModel(value = "OrderInvoice 对象", description = "订单-发票")
public class OrderInvoiceEntity extends CrudEntity<OrderInvoiceEntity>{

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    @Length(19)
    @TableField(value = "id")
    private Long id;

    @ApiModelProperty("订单号")
    @Length(19)
    @TableField(value = "order_no")
    private Long orderNo;

    @ApiModelProperty("发票类型：0->不开发票；1->电子发票；2->纸质发票")
    @Length(11)
    @TableField(value = "invoice_type")
    private Long invoiceType;

    @ApiModelProperty("")
    @Length(64)
    @TableField(value = "invoice_title")
    private String invoiceTitle;

    @ApiModelProperty("发表税号")
    @Length(64)
    @TableField(value = "invoice_no")
    private String invoiceNo;

    @ApiModelProperty("开票金额")
    @Length()
    @TableField(value = "amount")
    private BigDecimal amount;

    @ApiModelProperty("说明")
    @Length(64)
    @TableField(value = "remark")
    private String remark;

    @ApiModelProperty("开票明细")
    @Length(64)
    @TableField(value = "invoice_item")
    private String invoiceItem;

    @ApiModelProperty("用户邮箱用来接受字典发票")
    @Length(64)
    @TableField(value = "user_eamil")
    private String userEamil;

    @ApiModelProperty("应用ID")
    @Length(19)
    @TableField(value = "app_id")
    private Long appId;

    @ApiModelProperty("店铺ID")
    @Length(19)
    @TableField(value = "store_id")
    private Long storeId;

    @ApiModelProperty("时间戳")
    @Length()
    @TableField(value = "st")
    private Date st;


}
