package org.dows.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.order.entity.OrderInstance;
import org.dows.order.form.OrderInstanceTenantForm;
import org.dows.order.vo.OrderInstanceTenantOpVo;
import org.dows.order.vo.OrderInstanceTenantVo;

/**
 * 订单(OrderInstance)表数据库访问层
 *
 * @author lait
 * @since 2023-01-02 14:20:44
 */
@Mapper
public interface OrderInstanceMapper extends MybatisCrudMapper<OrderInstance> {


    IPage<OrderInstanceTenantVo> selectOrderInstancePage(@Param("page") Page page, @Param("param") OrderInstanceTenantForm adminForm);

    IPage<OrderInstanceTenantOpVo> selectOrderInstanceRePage(@Param("page") Page page, @Param("param") OrderInstanceTenantForm adminForm);

}

