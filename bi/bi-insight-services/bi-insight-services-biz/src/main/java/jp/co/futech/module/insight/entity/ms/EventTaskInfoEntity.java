package jp.co.futech.module.insight.entity.ms;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("task_event_info")
public class EventTaskInfoEntity {
    @TableId(value="task_id",type = IdType.ASSIGN_ID)
    private String taskId;
    private String taskName;
    private String taskAliasName;
    private String taskResTableName;
    private String minuDays;
    private String sqlMetaData;
    private Timestamp latestScheduleTs;
    private int latestScheduleStatus;
    private String taskDdlSql;
    private String taskDimension;
    private String taskFilterCriteria;
    private String taskExeSql;
    private int taskStatus;
    private int taskExecutionStatus;
    private String taskGroup;
    private int retryTimes;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String createId;
    private String createUsername;
    private String updateId;
    private String updateUsername;
    private String dimColumn;
    private String resColumn;
    private Integer isAuto; //0 表示可视化sql的任务，1 表示是自定义sql的任务



}
