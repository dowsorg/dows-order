package org.dows.order.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderMergeTableNoVo {

    @ApiModelProperty(value = "共计")
    private BigDecimal amount;

    @ApiModelProperty(value = "合并的桌台信息")
    private List<TableNoAmount> tableNoAmountList;


    @Data
   public static class TableNoAmount{

        @ApiModelProperty(value = "订单id")
        private String orderId;

        @ApiModelProperty(value = "订单号")
        private String orderNo;

        @ApiModelProperty(value = "桌台")
        private String tableNo;

        @ApiModelProperty(value = "金额")
        private BigDecimal totalAmount;

    }
}
