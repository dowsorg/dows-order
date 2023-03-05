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
public class OrderMyInstanceInfoVo implements Serializable {
    private static final long serialVersionUID = 4417623363378638870L;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "桌号/编号")
    private String tableNo;

    @ApiModelProperty(value = "类型 0:堂食 1:外卖 2:打包 3:扫码支付")
    private Integer type;

    @ApiModelProperty(value = "订单状态 1:待支付 2:已支付 3:已送达 4:已退款 5:已取消 6:已完成")
    private Integer orderStatus;

    @ApiModelProperty(value = "商品图片")
    private List<String> goodsUrl;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsNum;

    @ApiModelProperty(value = "共计")
    private BigDecimal total;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "订单时间")
    private Date dt;
}
