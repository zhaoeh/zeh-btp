package jp.co.futech.module.insight.po.req.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskSqlTemplateListReq {

    /**
     * <pre>
     * 指标类型
     * </pre>
     */
    private Integer	indType;

    /**
     * <pre>
     * 指标类型名称
     * </pre>
     */
    private String	indTypeName;

    /**
     * <pre>
     * data
     * </pre>
     */
    private List<TaskSqlTemplateDataReq> data;

}
