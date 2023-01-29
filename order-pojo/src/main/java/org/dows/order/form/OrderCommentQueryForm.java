package org.dows.order.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderCommentQueryForm implements Serializable {

    private static final long serialVersionUID = 2897255516939752527L;

    @ApiModelProperty(value = "门店id")
    private String storeId;
    @ApiModelProperty(value = "类型 0:全部 1:最新 2:好评 3:差评 4:有图有视频 5:回头客评价")
    private Integer type;
}