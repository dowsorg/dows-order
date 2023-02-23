package org.dows.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderInstanceTenantVo implements Serializable {
    private static final long serialVersionUID = 9172367651136981993L;

    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @JsonIgnore
    @ApiModelProperty(value = "客户账号id",hidden = true)
    private String accountId;
    @ApiModelProperty(value = "客户编号")
    private String accountNo;
    @ApiModelProperty(value = "客户姓名")
    private String userName;
    @ApiModelProperty(value = "订单类型 0:堂食|1:自营外卖|2:打包")
    private Integer type;
    @ApiModelProperty(value = "订单类型 0:堂食|1:自营外卖|2:打包")
    private String typeStr;
    @ApiModelProperty(value = "店铺id")
    private String storeId;
    @ApiModelProperty(value = "所属品牌")
    private Integer brand;
    @ApiModelProperty(value = "门店区域")
    private String storeDistrict;
    @ApiModelProperty(value = "门店模式 1直营 2加盟")
    private Integer storePattern;
    @ApiModelProperty(value = "就餐门店")
    private String storeName;
    @ApiModelProperty(value = "桌号")
    private String tableNo;
    @ApiModelProperty(value = "就餐人数")
    private Integer peoples;
    @ApiModelProperty(value = "支付渠道 1:微信|2:支付宝|3:储蓄卡")
    private Integer payChannel;
    @ApiModelProperty(value = "支付渠道 1:微信|2:支付宝|3:储蓄卡")
    private String payChannelStr;
    @ApiModelProperty(value = "菜品数量")
    private Integer foodNum;
    @ApiModelProperty(value = "订单状态")
    private String status;
    @ApiModelProperty(value = "订单金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "申请退款状态 0:拒绝退款|1:已退款")
    private Integer refundId;
    @ApiModelProperty(value = "申请退款状态 0:拒绝退款|1:已退款")
    private String refundStr;
    @ApiModelProperty(value = "申请退款备注")
    private String refundRemark;
    @ApiModelProperty(value = "下单时间")
    private Date orderDate;






















}
