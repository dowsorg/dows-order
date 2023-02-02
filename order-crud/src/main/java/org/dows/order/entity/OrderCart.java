package org.dows.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.dows.framework.crud.mybatis.CrudEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单-预购单(OrderCart)实体类
 *
 * @author lait
 * @since 2023-01-02 14:20:43
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "OrderCart对象", description = "订单-预购单")
public class OrderCart implements CrudEntity {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品副标题（卖点）")
    private String goodsSubTitle;

    @ApiModelProperty("商品主图")
    private String goodsPic;

    @ApiModelProperty("商品sku编号")
    private String goodsSkuId;

    @ApiModelProperty("产品id")
    private Long goodsSpuId;

    @ApiModelProperty("商品分类")
    private String goodsCategoryId;

    @ApiModelProperty("桌号唯一标识")
    private String tableNo;

    @ApiModelProperty("店铺ID")
    private Long storeId;

    @ApiModelProperty("客户账号ID")
    private Long accountId;

    @ApiModelProperty("应用ID")
    private String appId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("购买数量")
    private Integer quantity;

    @ApiModelProperty("添加到购物车的价格")
    private BigDecimal price;

    @ApiModelProperty("总价")
    private BigDecimal amount;

    @ApiModelProperty("人数")
    private Integer peoples;

    @ApiModelProperty("状态（下单之后对应商品就不应该显示在购物车了，到订单）")
    private Integer state;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("时间戳")
    private Date dt;

    @JsonIgnore
    //@TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("是否删除")
    private Boolean deleted;
}

