package org.dows.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderInstanceInfoVo implements Serializable {
    private static final long serialVersionUID = 858219061367286747L;


    @JsonSerialize(using= ToStringSerializer.class)
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "用户姓名")
    private String accountName;

    @ApiModelProperty(value = "用户地址")
    private String address;

    @ApiModelProperty(value = "桌号/编号")
    private String tableNo;

    @ApiModelProperty(value = "今日翻台次数")
    private Integer todayNum;

    @ApiModelProperty(value = "上菜分钟")
    private String menuMin;

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
    private List<GoodSpuInfoVo> goodSpuInfoList;

    @ApiModelProperty(value = "几种商品")
    private Integer spuCategory;

    @ApiModelProperty(value = "共几件")
    private Integer spuCount;

    @ApiModelProperty(value = "商家申请退款状态 0:拒绝退款 1:退款 2:用户申请退款(弹窗有申请退款订单 查看详情)")
    private Integer refund;

    @ApiModelProperty(value = "用户退款原因")
    private String applyRefundRemark;

    @ApiModelProperty(value = "用户上传的图片")
    private List<String> applyImageUrl;

    @ApiModelProperty(value = "下单日期")
    private Date dt;

    @ApiModelProperty(value = "第几单")
    private Integer accountOrderNum;

    @ApiModelProperty(value = "用户信息")
    private UserInfoVo userInfo;

}
