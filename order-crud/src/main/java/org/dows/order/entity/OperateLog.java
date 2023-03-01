package org.dows.order.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "OperateLog操作日志", description = "操作日志")
public class OperateLog extends Model<OperateLog> {

     private Long id;
     private Integer type;
     private String storeId;
     private String operator;
     private Date operatorTime;
     private String tableNo;
     private String orderNo;
     private String reason;
     private String foodName;
     private Date dt;



}
