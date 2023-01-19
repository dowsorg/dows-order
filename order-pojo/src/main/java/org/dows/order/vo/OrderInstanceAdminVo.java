package org.dows.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderInstanceAdminVo implements Serializable {
    private static final long serialVersionUID = 9172367651136981993L;

    @ApiModelProperty(value = "订单编号")
    private String orderId;
    @ApiModelProperty(value = "客户编号")
    private String accountNo;
    @ApiModelProperty(value = "客户姓名")
    private String userName;
    @ApiModelProperty(value = "订单类型")
    private Integer type;
    @ApiModelProperty(value = "所属品牌")
    private Integer brand;
    @ApiModelProperty(value = "门店区域")
    private String storeRegion;
    @ApiModelProperty(value = "门店模式")
    private Integer storeType;
    @ApiModelProperty(value = "就餐门店")
    private String storeName;
    @ApiModelProperty(value = "桌号")
    private String tableNo;
    @ApiModelProperty(value = "就餐人数")
    private Integer peoples;
    @ApiModelProperty(value = "下单方式")
    private Integer payChannel;
    @ApiModelProperty(value = "菜品数量")
    private Integer foodNum;
    @ApiModelProperty(value = "订单状态")
    private Integer status;
    @ApiModelProperty(value = "订单金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "下单时间")
    private Date orderDate;

    //退菜 赠菜 破损通用
    @ApiModelProperty(value = "退菜 赠菜 破损 通用名称")
    private String food;
    @ApiModelProperty(value = "退菜 赠菜 破损 通用数量")
    private Integer num;
    @ApiModelProperty(value = "退菜 赠菜 破损 通用原因")
    private String reason;
    @ApiModelProperty(value = "退菜 赠菜 破损 通用金额")
    private BigDecimal reAmount;
    @ApiModelProperty(value = "退菜 赠菜 破损 通用时间")
    private Date dateTime;
    @ApiModelProperty(value = "退菜 赠菜 破损 通用操作人")
    private String operator;





















}
