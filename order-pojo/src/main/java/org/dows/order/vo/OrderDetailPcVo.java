package org.dows.order.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDetailPcVo {

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单类型(0:堂食|1:自营外卖|2:打包)")
    private Integer type;

    @ApiModelProperty(value = "人数")
    private Integer peoples;

    @ApiModelProperty(value = "桌号/编号")
    private String tableNo;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "用户姓名")
    private String accountName;

    @ApiModelProperty(value = "用户地址")
    private String address;

    @ApiModelProperty(value = "订单来源 1:服务员下单|2:扫码下单")
    private Integer orderSource;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "小计")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "实收")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "用户信息")
    private UserInfoVo userInfo;

    @ApiModelProperty(value = "0:待出餐|1:已出餐|2:进行中|3:已完成")
    private Integer status;

    @ApiModelProperty(value = "备餐/出餐时间")
    private String diningTime;

    @ApiModelProperty(value = "商品信息")
    private List<GoodSpuInfoVo> goodSpuInfoList;

    @ApiModelProperty(value = "操作日志")
    private List<OperationLogVo> operationLogList;

    @ApiModelProperty(value = "骑手姓名")
    private String riderName;

    @ApiModelProperty(value = "骑手手机号")
    private String riderPhone;

    @ApiModelProperty(value = "下单日期")
    private Date dt;
}
