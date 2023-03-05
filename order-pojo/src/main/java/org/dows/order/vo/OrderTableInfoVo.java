package org.dows.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderTableInfoVo implements Serializable {
    private static final long serialVersionUID = -4170360318233108158L;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "几种商品")
    private Integer spuCategory;

    @ApiModelProperty(value = "共几件")
    private Integer spuCount;

    @ApiModelProperty(value = "订单备注")
    private String remark;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @ApiModelProperty(value = "下单日期")
    private Date dt;

    @ApiModelProperty(value = "小计")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "商品信息")
    private List<GoodSpuInfoVo> goodSpuInfoList;


}
