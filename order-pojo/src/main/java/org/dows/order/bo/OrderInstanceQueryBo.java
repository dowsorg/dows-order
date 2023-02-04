package org.dows.order.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderInstanceQueryBo implements Serializable {

    private static final long serialVersionUID = 7819126511796012141L;

    @ApiModelProperty(value = "订单类型(全部就不传 0:堂食|1:自营外卖|2:打包)")
    private Integer type;

    @ApiModelProperty(value = "用户id")
    private String accountId;

    @ApiModelProperty(value = "用户名")
    private String accountName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "日期")
    private Date date;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "店铺id")
    private String storeId;

    @ApiModelProperty(value = "桌号")
    private String tableNo;


}
