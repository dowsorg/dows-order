package org.dows.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.order.entity.OperateLog;
@Mapper
public interface OperateLogMapper extends MybatisCrudMapper<OperateLog> {
}
