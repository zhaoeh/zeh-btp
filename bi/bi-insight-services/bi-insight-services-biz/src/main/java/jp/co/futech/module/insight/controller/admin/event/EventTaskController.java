package jp.co.futech.module.insight.controller.admin.event;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.operatelog.core.annotations.OperateLog;
import jp.co.futech.framework.operatelog.core.enums.OperateTypeEnum;
import jp.co.futech.module.insight.annotation.OperationLogAnnotation;
import jp.co.futech.module.insight.aspect.OperationLogAspect;
import jp.co.futech.module.insight.constant.OperateConstant;
import jp.co.futech.module.insight.controller.admin.event.vo.EventTaskMetaVo;
import jp.co.futech.module.insight.controller.admin.event.vo.EventTaskPreviewVo;
import jp.co.futech.module.insight.controller.admin.event.vo.EventTaskVo;
import jp.co.futech.module.insight.entity.ms.EventTaskInfoEntity;
import jp.co.futech.module.insight.po.req.task.TaskOfflineReq;
import jp.co.futech.module.insight.po.req.task.TaskQueryReq;
import jp.co.futech.module.insight.po.req.task.TaskRerunReq;
import jp.co.futech.module.insight.service.event.EventTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 标签任务的Controller
 */
@RestController
@Tag(name = "大数据后台 - 事件任务")
@RequestMapping("/insight/event_task")
@Slf4j
public class EventTaskController {

    @Resource
    private EventTaskService eventTaskService;

    /**
     * 新增事件模板
     *
     * @return
     */
    @OperateLog(type = OperateTypeEnum.CREATE)
    @OperationLogAnnotation(operModule = OperateConstant.OP_MODULE_TASK, operType = OperateConstant.OP_TYPE_ADD, operDesc = "新增事件")
    @PostMapping("/create")
    @Operation(summary = "新增事件模板")
    public CommonResult<?> addEventTask(@RequestBody EventTaskVo createReqVO) {
        String guid = eventTaskService.createAuto(createReqVO);
        OperationLogAspect.setContent(guid);
        return CommonResult.success(guid);
    }

    /**
     * 新增自定义事件模板
     *
     * @return
     */
    @OperateLog(type = OperateTypeEnum.CREATE)
    @OperationLogAnnotation(operModule = OperateConstant.OP_MODULE_TASK, operType = OperateConstant.OP_TYPE_ADD, operDesc = "新增自定义事件")
    @PostMapping("/create/customer")
    @Operation(summary = "新增自定义事件模板")
    public CommonResult<?> addCustomerEventTask(@RequestBody EventTaskVo createReqVO) {
        String guid = eventTaskService.createCustomer(createReqVO);
        OperationLogAspect.setContent(guid);
        return CommonResult.success(guid);
    }

    /**
     * 获取SQL模板
     *
     * @return
     */
    @GetMapping("/sql_template")
    @Operation(summary = "获取SQL模板")
    @OperateLog(enable = false)
    public CommonResult<?> getSqlTemplate() {
        List<?> list = eventTaskService.getSqlTemplate();
        var result = Optional.ofNullable(list).map(e -> e.stream().findFirst().orElse(null)).orElse(null);
        return CommonResult.success(result);
    }

    @OperateLog(type = OperateTypeEnum.UPDATE)
    @OperationLogAnnotation(operModule = OperateConstant.OP_MODULE_TASK, operType = OperateConstant.OP_TYPE_OFFLINE, operDesc = "事件下线")
    @PostMapping("/offline")
    @Operation(summary = "事件下线")
    public CommonResult<?> offlineTask(@RequestBody TaskOfflineReq taskOfflineReq) {
        String taskId = taskOfflineReq.getTaskId();
        log.info("offline taskId:" + taskId);
        eventTaskService.offlineTask(taskId);
        OperationLogAspect.setContent(taskId);
        return CommonResult.success("OK");
    }

    @OperateLog(type = OperateTypeEnum.UPDATE)
    @OperationLogAnnotation(operModule = OperateConstant.OP_MODULE_TASK, operType = OperateConstant.OP_TYPE_ONLINE, operDesc = "事件上线")
    @PostMapping("/online")
    @Operation(summary = "事件上线")
    public CommonResult<?> onlineTask(@RequestBody TaskOfflineReq taskOfflineReq) {
        String taskId = taskOfflineReq.getTaskId();
        log.info("online taskId:" + taskId);
        eventTaskService.onlineTask(taskId);
        OperationLogAspect.setContent(taskId);
        return CommonResult.success("OK");
    }

    @OperateLog(type = OperateTypeEnum.UPDATE)
    @OperationLogAnnotation(operModule = OperateConstant.OP_MODULE_TASK, operType = OperateConstant.OP_TYPE_RERUN, operDesc = "事件重跑")
    @PostMapping("/rerun")
    @Operation(summary = "事件重跑")
    public CommonResult<?> rerun(@RequestBody TaskRerunReq taskRerunReq) {
        String taskId = taskRerunReq.getTaskId();
        String startDate = taskRerunReq.getStartDate();
        String endDate = taskRerunReq.getEndDate();
        eventTaskService.rerunTask(taskId, startDate, endDate);
        OperationLogAspect.setContent(taskId);
        return CommonResult.success("OK");
    }

    @PostMapping("/data_metadata")
    @Operation(summary = "获取元数据")
    @OperateLog(enable = false)
    public CommonResult<?> getMetaData(@RequestBody EventTaskMetaVo eventTaskMetaVo) {
        String metaType = eventTaskMetaVo.getMetaType();
        Set<String> tables = eventTaskMetaVo.getTables();
        JSONObject metaData = eventTaskService.getMetaData(metaType, tables);
        return CommonResult.success(metaData);
    }

    @PostMapping("/pagination")
    @Operation(summary = "分页查询标签任务")
    @OperateLog(enable = false)
    public CommonResult<?> searchByPagination(@RequestBody TaskQueryReq req) {
        PageResult<EventTaskInfoEntity> eventTaskInfoEntityIPage = this.eventTaskService.searchByPagination(req);
        return CommonResult.success(eventTaskInfoEntityIPage);
    }

    @OperateLog(type = OperateTypeEnum.DELETE)
    @OperationLogAnnotation(operModule = OperateConstant.OP_MODULE_TASK, operType = OperateConstant.OP_TYPE_DELETE, operDesc = "事件删除")
    @PutMapping("/delete/{taskId}")
    @Operation(summary = "事件删除")
    public CommonResult<?> deleteEventTask(@PathVariable("taskId") String taskId) {
        eventTaskService.deleteEventTask(taskId);
        OperationLogAspect.setContent(taskId);
        return CommonResult.success("OK");
    }

    @OperateLog(enable = false)
    @GetMapping("/get_task_info")
    @Operation(summary = "获取事件详细信息")
    public CommonResult<?> getEventTaskInfo(@RequestParam("taskId") String taskId){
        JSONObject json=eventTaskService.getEventTaskInfo(taskId);
        return CommonResult.success(json);
    }

    @OperateLog(enable = false)
    @PostMapping("/preview")
    @Operation(summary = "预览数据")
    public CommonResult<?> getPreview(@RequestBody EventTaskPreviewVo eventTaskPreviewVo){
        JSONArray object=eventTaskService.getPreview(eventTaskPreviewVo.getFields(),eventTaskPreviewVo.getTaskId());
        return CommonResult.success(object);
    }
    @OperateLog(enable = false)
    @PostMapping("/charturl")
    @Operation(summary = "获取chart的url")
    public CommonResult<?> chartUrl(@RequestBody String taskId){
        String chartUrl = eventTaskService.getChartUrl(JSONObject.parseObject(taskId).getString("taskId"));
        return CommonResult.success(chartUrl);
    }
}
