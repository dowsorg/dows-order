package org.dows.order.form;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderMergeTableNoForm {

    @ApiModelProperty(value = "合并桌台信息")
    private List<String> orderNoList;




}
