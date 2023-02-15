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
public class OrderInstanceDetailVo implements Serializable {
    private static final long serialVersionUID = 7672525267055493678L;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "取餐码")
    private String tableNo;

    @ApiModelProperty(value = "支付状态 0:未支付 1:已支付")
    private Integer payStatus;

    @ApiModelProperty(value = "订单状态 1:商家已接单 2:待配送员接单 3:配送中 4:已完成")
    private Integer orderStatus;

    @ApiModelProperty(value = "退款状态 1:退款中 2:商家拒绝退款 3:退款成功")
    private Integer refundStatus;

    @ApiModelProperty(value = "商家拒绝退款原因")
    private String refundReason;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refundAmount;

    @ApiModelProperty(value = "用户姓名")
    private String accountName;

    @ApiModelProperty(value = "用户手机号")
    private String accountPhone;

    @ApiModelProperty(value = "用户地址")
    private String accountAddress;

    @ApiModelProperty(value = "商家名称")
    private String storeName;

    @ApiModelProperty(value = "商家地址")
    private String storeAddress;

    @ApiModelProperty(value = "商家电话")
    private String  storePhone;

    @ApiModelProperty(value = "菜品信息")
    private List<GoodInfo> goodInfoList;

    @ApiModelProperty(value = "商品总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "包装费")
    private BigDecimal packingPrice;

    @ApiModelProperty(value = "配送费")
    private BigDecimal givePrice;

    @ApiModelProperty(value = "抵扣劵(优惠券)")
    private BigDecimal deductionPrice;

    @ApiModelProperty(value = "积分")
    private Integer integralPrice;

    @ApiModelProperty(value = "合计")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "餐具数量")
    private Integer tableWare;

    @ApiModelProperty(value = "订单信息")
    private OrderInfo orderInfo;


    @Data
    public static class GoodInfo implements Serializable{

        private static final long serialVersionUID = 1344012350388075626L;

        @ApiModelProperty(value = "商品图片")
        private String image;

        @ApiModelProperty(value = "商品名称")
        private String spuName;

        @ApiModelProperty(value = "商品副名称")
        private String subSpuName;

        @ApiModelProperty(value = "商品数量")
        private Integer num;

        @ApiModelProperty(value = "商品价格")
        private BigDecimal price;
    }

    @Data
    public static class OrderInfo implements Serializable{
        private static final long serialVersionUID = -3415008070970798424L;

        @ApiModelProperty(value = "订单号")
        private String orderNo;

        @ApiModelProperty(value = "就餐方式 0:堂食|1:自营外卖|2:打包")
        private Integer diningMode;

        @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
        @ApiModelProperty(value = "下单时间")
        private Date dt;

        @ApiModelProperty(value = "赠送积分")
        private Integer integral;

        @ApiModelProperty(value = "积分归属")
        private String acceptIntegral;



    }






}
