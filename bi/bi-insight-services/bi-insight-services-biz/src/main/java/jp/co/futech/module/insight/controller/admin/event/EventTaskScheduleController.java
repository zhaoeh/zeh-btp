package jp.co.futech.module.insight.controller.admin.event;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.operatelog.core.annotations.OperateLog;
import jp.co.futech.framework.operatelog.core.enums.OperateTypeEnum;
import jp.co.futech.framework.tenant.core.annotation.IgnoreTenantRequest;
import jp.co.futech.module.insight.annotation.OperationLogAnnotation;
import jp.co.futech.module.insight.aspect.OperationLogAspect;
import jp.co.futech.module.insight.constant.OperateConstant;
import jp.co.futech.module.insight.controller.admin.event.vo.TaskScheduleResultVo;
import jp.co.futech.module.insight.service.event.EventTaskScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="调度系统返回的调度状态")
@RequestMapping("/insight/schedule")
public class EventTaskScheduleController {

    protected HttpServletRequest request;

    @Autowired
    private EventTaskScheduleService eventTaskScheduleService;
    @OperationLogAnnotation(operModule = OperateConstant.OP_MODULE_TASK, operType = OperateConstant.OP_TYPE_SCHEDULE, operDesc = "事件任务调度结果")
    @PostMapping("/result")
    @Operation(summary = "事件任务调度结果")
    @PermitAll
    @IgnoreTenantRequest
    @OperateLog(type = OperateTypeEnum.UPDATE)
    public CommonResult<?> taskScheduleResult(@RequestBody TaskScheduleResultVo taskScheduleResultVo){
        eventTaskScheduleService.updateTaskInfo(taskScheduleResultVo);
        OperationLogAspect.setContent(taskScheduleResultVo.getTask_id());
        return CommonResult.success("OK");
    }
}
