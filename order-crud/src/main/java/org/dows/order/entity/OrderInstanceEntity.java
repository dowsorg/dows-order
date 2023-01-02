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
@TableName(value = "orderinstance")
@ApiModel(value = "OrderInstance 对象", description = "订单")
public class OrderInstanceEntity extends CrudEntity<OrderInstanceEntity>{

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("自增主键ID")
    @Length(19)
    @TableField(value = "id")
    private Long id;

    @ApiModelProperty("分布式ID")
    @Length(64)
    @TableField(value = "order_id")
    private String orderId;

    @ApiModelProperty("桌号唯一标识")
    @Length(64)
    @TableField(value = "table_id")
    private String tableId;

    @ApiModelProperty("客户账号ID")
    @Length(64)
    @TableField(value = "account_id")
    private String accountId;

    @ApiModelProperty("店铺ID")
    @Length(64)
    @TableField(value = "store_id")
    private String storeId;

    @ApiModelProperty("应用ID")
    @Length(64)
    @TableField(value = "app_id")
    private String appId;

    @ApiModelProperty("租户号")
    @Length(64)
    @TableField(value = "tenant_id")
    private String tenantId;

    @ApiModelProperty("备注")
    @Length(64)
    @TableField(value = "remark")
    private String remark;

    @ApiModelProperty("总金额")
    @Length()
    @TableField(value = "amount")
    private BigDecimal amount;

    @ApiModelProperty("人数")
    @Length(11)
    @TableField(value = "peoples")
    private Long peoples;

    @ApiModelProperty("订单类型(0:堂食|1:外卖|2:打包)")
    @Length(11)
    @TableField(value = "typ")
    private Long typ;

    @ApiModelProperty("订单状态(0:制作中|1:制作完成|2:待支付|3:已支付|4:进行中|5:已结束)")
    @Length(11)
    @TableField(value = "status")
    private Long status;

    @ApiModelProperty("时间戳")
    @Length()
    @TableField(value = "dt")
    private Date dt;


}
