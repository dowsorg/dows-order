package org.dows.order.rest.tenant;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.order.OperateLogBiz;
import org.dows.order.form.OperateLogQueryForm;
import org.dows.order.form.OrderInstanceTenantForm;
import org.dows.order.vo.OperateLogVo;
import org.dows.order.vo.OrderInstanceTenantVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "操作日志")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("tenant/operateLog")
public class TenantOperateLogRest {


    private final OperateLogBiz operateLogBiz;

    /**
     * 后台日志分页列表
     * @param
     * @return
     */
    @PostMapping("/queryOperateLogPage")
    @ApiOperation("后台日志分页列表")
    public Response<IPage<OperateLogVo>> queryOperateLogPage(@RequestBody OperateLogQueryForm queryForm){
        return Response.ok(operateLogBiz.queryOperateLogPage(queryForm));
    }

}
