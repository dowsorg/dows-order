package org.dows.order.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OrderInstanceTenantForm extends QueryParam {

    @ApiModelProperty(value = "1:正常 2:退款")
    private Integer orderRefund;
    @ApiModelProperty(value = "2:退菜明细 3:赠送明细 4:破损列表")
    private Integer orderOpType;
    @ApiModelProperty(value = "订单编号")
    private String orderId;
    @ApiModelProperty(value = "门店id")
    private String storeId;
    @ApiModelProperty(value = "门店区域")
    private Integer storeRegion;
    @ApiModelProperty(value = "门店模式")
    private Integer storeMode;
    @ApiModelProperty(value = "所属品牌")
    private Integer brand;
    @ApiModelProperty(value = "订单类型")
    private Integer orderType;
    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;
    @ApiModelProperty(value = "订单开始时间")
    private Date startDate;
    @ApiModelProperty(value = "订单结束时间")
    private Date endDate;

}
