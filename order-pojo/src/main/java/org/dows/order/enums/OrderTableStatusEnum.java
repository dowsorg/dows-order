package org.dows.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  OrderTableStatusEnum {
    //桌台状态 1:上菜中 2:菜上齐 3:已超时 4:已结账
    menu_in("上菜中",1),
    finish_menu("菜上齐",2),
    time_out("已超时",3),
    closed("已结账",4),
    ;

    private String name;

    private Integer code;


}
