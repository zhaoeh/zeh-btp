package jp.co.futech.module.insight.service.event;

import jp.co.futech.module.insight.controller.admin.event.vo.TaskScheduleResultVo;

public interface EventTaskScheduleService {
    void updateTaskInfo(TaskScheduleResultVo taskScheduleResultVo);
}
