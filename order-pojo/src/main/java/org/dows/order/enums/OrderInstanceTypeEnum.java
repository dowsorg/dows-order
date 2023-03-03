package org.dows.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

//订单状态
@Getter
@AllArgsConstructor
public enum OrderInstanceTypeEnum {
    //(0:制作中|1:制作完成|2:待支付|3:已支付|4:进行中|5:已结束)
    make("制作中",0),
    make_finish("制作完成",1),
    pay_in("进行中",2),
    over("已结束",3),
    ;

    private String name;

    private Integer code;
}
