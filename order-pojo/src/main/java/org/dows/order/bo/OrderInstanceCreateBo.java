package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderInstanceCreateBo implements Serializable {
    private static final long serialVersionUID = -7194552277994797454L;

    @ApiModelProperty(value = "0:堂食|1:自营外卖|2:打包")
    private Integer type;

    @ApiModelProperty(value = "人数")
    private Integer peoples;

    @ApiModelProperty(value = "1:大店 2：小店")
    private Integer orderStore;

    @ApiModelProperty(value = "店铺id")
    private String storeId;

    @ApiModelProperty(value = "店铺桌号")
    private String tableNo;

    @ApiModelProperty(value = "1:用户 2：店员")
    private Integer operationType;

    @ApiModelProperty(value = "账号id")
    private String accountId;

    @ApiModelProperty(value = "订单备注")
    private String remark;

    @ApiModelProperty(value = "商品信息")
    private List<GoodSpuInfo> goodSpuInfoList;

    @Data
    public static class GoodSpuInfo implements Serializable{

        private static final long serialVersionUID = -8883985643882456615L;

        @ApiModelProperty(value = "商品id")
        private Long goodSpuId;

        @ApiModelProperty(value = "商品数量")
        private Integer quantity;

        @ApiModelProperty(value = "商品备注")
        private String remark;

    }

}