package jp.co.futech.module.insight.entity.ms;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("task_event_schedule_info")
public class EventTaskScheduleInfoEntity {
    @TableId(value = "task_id", type = IdType.ASSIGN_ID)
    private String taskId;
    private String taskCate;
    private int scheduleResult;
    private Timestamp startTime;
    private Timestamp endTime;
    private String logInfo;
}
