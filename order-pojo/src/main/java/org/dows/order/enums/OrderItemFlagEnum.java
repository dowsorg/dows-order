package org.dows.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderItemFlagEnum {
    //1:划菜 2:退菜 3:赠送 4:报损
    stroke_menu("划菜",1),
    return_menu("退菜",2),
    give("赠送",3),
    reporting("报损",4),
    ;

    private String name;

    private Integer code;
}
