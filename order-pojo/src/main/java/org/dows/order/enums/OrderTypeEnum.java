package org.dows.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderTypeEnum {

    food(0,"堂食"),
    takeOut(1,"外卖"),
    pack(2,"打包"),
    ;


    private Integer code;
    private String name;

}
