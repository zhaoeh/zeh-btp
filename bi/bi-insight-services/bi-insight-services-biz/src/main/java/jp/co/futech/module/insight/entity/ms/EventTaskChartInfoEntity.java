package jp.co.futech.module.insight.entity.ms;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("task_chart_info")
public class EventTaskChartInfoEntity {
    @TableId(value="task_id", type = IdType.ASSIGN_ID)
    private String taskId;
    private int datasetId;
    private int sliceId;
    private int datasetStatus;
    private String chartUrl;
    private Timestamp createTime;

}
