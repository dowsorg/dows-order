package org.dows.order.form;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderMergeTableNoForm {


    @ApiModelProperty(value = "店铺id")
    private String storeId;

    @ApiModelProperty(value = "合并桌台")
    private List<String> tableNoList;




}
