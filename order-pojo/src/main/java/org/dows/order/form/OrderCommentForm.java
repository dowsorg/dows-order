package org.dows.order.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.List;

/**
* @description 
*
* @author 
* @date 2023年1月2日 下午2:04:57
*/
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "OrderComment 表单对象", description = "订单-评价")
public class OrderCommentForm implements Serializable{

    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("图片列表")
    private List<String> pics;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("店铺ID")
    private String storeId;
    @ApiModelProperty("评论分数 1:2:3:4:5")
    private Integer score;

    @JsonIgnore
    private String accountId;

}
