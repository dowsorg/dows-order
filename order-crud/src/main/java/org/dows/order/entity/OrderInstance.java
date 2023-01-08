package org.dows.order.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.dows.framework.crud.mybatis.CrudEntity;

/**
 * 订单(OrderInstance)实体类
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
@ApiModel(value = "OrderInstance对象", description = "订单")
public class OrderInstance implements CrudEntity {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("自增主键ID")
    private Long id;

    @ApiModelProperty("分布式ID")
    private String orderId;

    @ApiModelProperty("桌号唯一标识")
    private String tableId;

    @ApiModelProperty("桌号")
    private String tableNo;

    @ApiModelProperty("客户账号ID")
    private String accountId;

    @ApiModelProperty("店铺ID")
    private String storeId;

    @ApiModelProperty("应用ID")
    private String appId;

    @ApiModelProperty("租户号")
    private String tenantId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("总金额")
    private BigDecimal amount;

    @ApiModelProperty("人数")
    private Integer peoples;

    @ApiModelProperty("订单类型(0:堂食|1:外卖|2:打包)")
    private Integer type;

    @ApiModelProperty("订单状态(0:制作中|1:制作完成|2:待支付|3:已支付|4:进行中|5:已结束)")
    private Integer status;

    @ApiModelProperty("'支付方式'")
    private Integer payChannel;

    @ApiModelProperty("支付状态")
    private Integer payState;

    @ApiModelProperty("支付时间")
    private Date payTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("时间戳")
    private Date dt;
    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("是否删除")
    private Boolean deleted;

}

