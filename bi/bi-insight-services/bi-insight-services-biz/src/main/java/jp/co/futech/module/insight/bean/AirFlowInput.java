package jp.co.futech.module.insight.bean;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Heng.zhang
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirFlowInput {
    @JSONField(name = "task_id")
    private String	taskId;
    
    @JSONField(name = "delete_sql")
    private String deleteSql;

    @JSONField(name = "insert_sql")
    private String insertSql;

    @JSONField(name = "select_sql")
    private String selectSql;
    @JSONField(name = "task_type")
    private String	taskType;

    @JSONField(name = "task_name")
    private String	taskName;

    @JSONField(name = "operate_time")
    private String	operateTime;

    @JSONField(name = "task_cate")
    private String	taskCate;

    @JSONField(name = "parent_task_ids")
    private String	parentTaskIds;

    @JSONField(name = "date_from")
    private String	dateFrom;

    @JSONField(name = "date_to")
    private String	dateTo;

}