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
 * 订单-发票(OrderInvoice)实体类
 *
 * @author lait
 * @since 2023-01-02 14:20:44
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "OrderInvoice对象", description = "订单-发票")
public class OrderInvoice implements CrudEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("发票类型：0->不开发票；1->电子发票；2->纸质发票")
    private Integer invoiceType;

    @ApiModelProperty("")
    private String invoiceTitle;

    @ApiModelProperty("发表税号")
    private String invoiceNo;

    @ApiModelProperty("开票金额")
    private BigDecimal amount;

    @ApiModelProperty("说明")
    private String remark;

    @ApiModelProperty("开票明细")
    private String invoiceItem;

    @ApiModelProperty("用户邮箱用来接受字典发票")
    private String userEamil;

    @ApiModelProperty("应用ID")
    private Long appId;

    @ApiModelProperty("店铺ID")
    private Long storeId;

    @ApiModelProperty("时间戳")
    private Date st;

    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("是否删除")
    private Boolean deleted;

}

