package org.dows.order.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderInstanceUpdateBo implements Serializable {
    private static final long serialVersionUID = 4871728367641792033L;

    private String orderId;

    private Integer status;

    private Date payTime;
}
