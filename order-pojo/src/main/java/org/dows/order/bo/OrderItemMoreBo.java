package org.dows.order.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItemMoreBo implements Serializable {
    private static final long serialVersionUID = -3125691632771476681L;

    private String key;

    private String val;
}
