package org.dows.order.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class OrderMyForm implements Serializable {
    private static final long serialVersionUID = -8303949280157098653L;


    @ApiModelProperty(value = "客户id")
    private String accountId;

    @ApiModelProperty(value = "类型 全部不传 0:堂食 1:外卖 2:打包 3:扫码支付")
    private Integer type;

    @ApiModelProperty(value = "第一页不传值 翻页传最后一条数据的orderid （分页用的）")
    private Long orderId;
}
