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
@ApiModel(value = "OrderInvoice 表单对象", description = "订单-发票")
public class OrderInvoiceForm implements Serializable{
    @JsonIgnore
    @Length(19)
    private Long id;
    @ApiModelProperty("订单号")
    @Length(19)
    private Long orderNo;
    @ApiModelProperty("发票类型：0->不开发票；1->电子发票；2->纸质发票")
    @Length(11)
    private Long invoiceType;
    @ApiModelProperty("")
    @Length(64)
    private String invoiceTitle;
    @ApiModelProperty("发表税号")
    @Length(64)
    private String invoiceNo;
    @ApiModelProperty("开票金额")
    @Length()
    private BigDecimal amount;
    @ApiModelProperty("说明")
    @Length(64)
    private String remark;
    @ApiModelProperty("开票明细")
    @Length(64)
    private String invoiceItem;
    @ApiModelProperty("用户邮箱用来接受字典发票")
    @Length(64)
    private String userEamil;
    @ApiModelProperty("应用ID")
    @Length(19)
    private Long appId;
    @ApiModelProperty("店铺ID")
    @Length(19)
    private Long storeId;
    @ApiModelProperty("时间戳")
    @Length()
    private Date st;

}
