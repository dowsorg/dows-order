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

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "用户姓名")
    private String accountName;

    @ApiModelProperty(value = "桌号/编号")
    private String tableNo;

    @ApiModelProperty(value = "人数")
    private Integer peoples;

    @ApiModelProperty(value = "订单类型(0:堂食|1:自营外卖|2:打包)")
    private Integer type;

    @ApiModelProperty(value = "支付渠道 1:支付宝|2:微信")
    private Integer payChannel;

    @ApiModelProperty(value = "订单来源 1:服务员下单|2:扫码下单")
    private Integer orderSource;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "小计")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "实收")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "0:待出餐|1:已出餐|2:进行中|3:已完成")
    private Integer status;

    @ApiModelProperty(value = "备餐/出餐时间")
    private String diningTime;

    @ApiModelProperty(value = "商品信息")
    private List<GoodSpuInfo> goodSpuInfoList;

    @ApiModelProperty(value = "几种商品")
    private Integer spuCategory;

    @ApiModelProperty(value = "共几件")
    private Long spuCount;

    @ApiModelProperty(value = "下单日期")
    private Date dt;

    @ApiModelProperty(value = "用户信息")
    private UserInfo userInfo;

    @Data
    public static class UserInfo implements Serializable{
        @ApiModelProperty(value = "姓名")
        private String name;
        @ApiModelProperty(value = "性别")
        private Integer sex;
        @ApiModelProperty(value = "生日")
        private String birthday;
        @ApiModelProperty(value = "手机号")
        private String phone;
        @ApiModelProperty(value = "注册时间")
        private Date createDate;
        @ApiModelProperty(value = "总订单量")
        private Integer orderNum;
        @ApiModelProperty(value = "消费总额")
        private BigDecimal amount;
        @ApiModelProperty(value = "最近下单")
        private String dateOf;
    }

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

        @ApiModelProperty(value = "备注")
        private String remark;
    }
}
