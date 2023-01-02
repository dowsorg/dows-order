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
@ApiModel(value = "OrderCart 表单对象", description = "订单-预购单")
public class OrderCartForm implements Serializable{
    @JsonIgnore
    @Length(19)
    private Long id;
    @ApiModelProperty("商品名称")
    @Length(64)
    private String goodsName;
    @ApiModelProperty("商品副标题（卖点）")
    @Length(64)
    private String goodsSubTitle;
    @ApiModelProperty("商品主图")
    @Length(64)
    private String goodsPic;
    @ApiModelProperty("商品sku编号")
    @Length(64)
    private String goodsSkuId;
    @ApiModelProperty("产品id")
    @Length(64)
    private String goodsSpuId;
    @ApiModelProperty("商品分类")
    @Length(64)
    private String goodsCategoryId;
    @ApiModelProperty("桌号唯一标识")
    @Length(64)
    private String tableId;
    @ApiModelProperty("店铺ID")
    @Length(64)
    private String storeId;
    @ApiModelProperty("客户账号ID")
    @Length(64)
    private String accountId;
    @ApiModelProperty("应用ID")
    @Length(64)
    private String appId;
    @ApiModelProperty("备注")
    @Length(64)
    private String remark;
    @ApiModelProperty("购买数量")
    @Length()
    private BigDecimal quantity;
    @ApiModelProperty("添加到购物车的价格")
    @Length()
    private BigDecimal price;
    @ApiModelProperty("总价")
    @Length()
    private BigDecimal amount;
    @ApiModelProperty("人数")
    @Length(11)
    private Long peoples;
    @ApiModelProperty("状态（下单之后对应商品就不应该显示在购物车了，到订单）")
    @Length(11)
    private Long state;
    @JsonIgnore
    @Length()
    private Date dt;

}
