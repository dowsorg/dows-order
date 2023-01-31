package org.dows.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderInstanceInfoVo implements Serializable {
    private static final long serialVersionUID = 858219061367286747L;

    @ApiModelProperty(value = "账号名")
    private String accountName;

    @ApiModelProperty(value = "编号")
    private String tableId;

    @ApiModelProperty(value = "人数")
    private Integer peoples;

    @ApiModelProperty(value = "支付渠道 1:支付宝|2:微笑")
    private Integer payChannel;

    @ApiModelProperty(value = "订单来源 1:服务员下单|2:扫码下单")
    private Integer orderSource;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "小计")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "实收")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "0:制作中|1:制作完成|2:待支付|3:已支付|4:进行中|5:已结束")
    private Integer status;

    @ApiModelProperty(value = "商品信息")
    private List<GoodSpuInfo> goodSpuInfoList;

    @ApiModelProperty(value = "下单日期")
    private Date createDate;

    @Data
    public static class GoodSpuInfo implements Serializable{

        private static final long serialVersionUID = 1247901568842557748L;

        @ApiModelProperty(value = "订单项id")
        private Long orderItemId;

        @ApiModelProperty(value = "商品信息")
        private String goodName;

        @ApiModelProperty(value = "数量")
        private Integer quantity;

        @ApiModelProperty(value = "价格")
        private BigDecimal price;

        @ApiModelProperty(value = "是否化菜(0:未上菜 1:已上菜 2:退菜 3:赠送 4:报损)")
        private Integer flag;
    }
}
