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
@TableName(value = "ordercart")
@ApiModel(value = "OrderCart 对象", description = "订单-预购单")
public class OrderCartEntity extends CrudEntity<OrderCartEntity>{

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    @Length(19)
    @TableField(value = "id")
    private Long id;

    @ApiModelProperty("商品名称")
    @Length(64)
    @TableField(value = "goods_name")
    private String goodsName;

    @ApiModelProperty("商品副标题（卖点）")
    @Length(64)
    @TableField(value = "goods_sub_title")
    private String goodsSubTitle;

    @ApiModelProperty("商品主图")
    @Length(64)
    @TableField(value = "goods_pic")
    private String goodsPic;

    @ApiModelProperty("商品sku编号")
    @Length(64)
    @TableField(value = "goods_sku_id")
    private String goodsSkuId;

    @ApiModelProperty("产品id")
    @Length(64)
    @TableField(value = "goods_spu_id")
    private String goodsSpuId;

    @ApiModelProperty("商品分类")
    @Length(64)
    @TableField(value = "goods_category_id")
    private String goodsCategoryId;

    @ApiModelProperty("桌号唯一标识")
    @Length(64)
    @TableField(value = "table_id")
    private String tableId;

    @ApiModelProperty("店铺ID")
    @Length(64)
    @TableField(value = "store_id")
    private String storeId;

    @ApiModelProperty("客户账号ID")
    @Length(64)
    @TableField(value = "account_id")
    private String accountId;

    @ApiModelProperty("应用ID")
    @Length(64)
    @TableField(value = "app_id")
    private String appId;

    @ApiModelProperty("备注")
    @Length(64)
    @TableField(value = "remark")
    private String remark;

    @ApiModelProperty("购买数量")
    @Length()
    @TableField(value = "quantity")
    private BigDecimal quantity;

    @ApiModelProperty("添加到购物车的价格")
    @Length()
    @TableField(value = "price")
    private BigDecimal price;

    @ApiModelProperty("总价")
    @Length()
    @TableField(value = "amount")
    private BigDecimal amount;

    @ApiModelProperty("人数")
    @Length(11)
    @TableField(value = "peoples")
    private Long peoples;

    @ApiModelProperty("状态（下单之后对应商品就不应该显示在购物车了，到订单）")
    @Length(11)
    @TableField(value = "state")
    private Long state;

    @ApiModelProperty("时间戳")
    @Length()
    @TableField(value = "dt")
    private Date dt;


}
