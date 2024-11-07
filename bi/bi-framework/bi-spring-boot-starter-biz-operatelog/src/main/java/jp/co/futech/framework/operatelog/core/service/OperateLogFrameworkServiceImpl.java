package jp.co.futech.framework.operatelog.core.service;

import cn.hutool.core.bean.BeanUtil;
import jp.co.futech.module.system.api.logger.OperateLogApi;
import jp.co.futech.module.system.api.logger.dto.OperateLogCreateReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志 Framework Service 实现类
 *
 * 基于 {@link OperateLogApi} 实现，记录操作日志
 *
 * @author futech.co.jp
 */
@RequiredArgsConstructor
public class OperateLogFrameworkServiceImpl implements OperateLogFrameworkService {

    private final OperateLogApi operateLogApi;

    @Override
    @Async
    public void createOperateLog(OperateLog operateLog) {
        OperateLogCreateReqDTO reqDTO = BeanUtil.toBean(operateLog, OperateLogCreateReqDTO.class);
        operateLogApi.createOperateLog(reqDTO);
    }

}
