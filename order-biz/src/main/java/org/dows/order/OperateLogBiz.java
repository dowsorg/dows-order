package org.dows.order;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.order.form.OperateLogQueryForm;
import org.dows.order.mapper.OperateLogMapper;
import org.dows.order.vo.OperateLogVo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperateLogBiz {

    private final OperateLogMapper operateLogMapper;

    public IPage<OperateLogVo> queryOperateLogPage(OperateLogQueryForm queryForm){
        return new Page<OperateLogVo>();
    }
}
