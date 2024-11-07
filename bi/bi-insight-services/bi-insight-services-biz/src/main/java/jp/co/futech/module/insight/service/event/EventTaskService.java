package jp.co.futech.module.insight.service.event;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.module.insight.controller.admin.event.vo.EventTaskVo;
import jp.co.futech.module.insight.entity.ms.EventTaskInfoEntity;
import jp.co.futech.module.insight.po.req.task.TaskQueryReq;
import jp.co.futech.module.insight.po.req.task.TaskSqlTemplateListReq;

import java.util.List;
import java.util.Set;

/**
 * 获取SQL模板
 */
public interface EventTaskService {

    /**
     * 获取SQL模板
     * @return
     */
    List<TaskSqlTemplateListReq> getSqlTemplate();
    String createAuto(EventTaskVo eventTaskVo);
    String createCustomer(EventTaskVo eventTaskVo);
    void offlineTask(String taskId);
    void onlineTask(String taskId);
    void rerunTask(String taskId, String startDate, String endDate);
    JSONObject getMetaData(String metaType, Set<String> tables);
    PageResult<EventTaskInfoEntity> searchByPagination(TaskQueryReq req);
    void deleteEventTask(String taskId);
    JSONObject getEventTaskInfo(String taskId);
    JSONArray getPreview(List<String> whereColumnName, String taskId);
    String getChartUrl(String taskId);

}
