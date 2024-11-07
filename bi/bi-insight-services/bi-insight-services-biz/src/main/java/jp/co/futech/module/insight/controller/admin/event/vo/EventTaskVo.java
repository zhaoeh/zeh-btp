package jp.co.futech.module.insight.controller.admin.event.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 *  FiltersCol
 * </pre>
 * @author zh
 * @verison $Id: FiltersCol v 0.1 2024-07-05 12:30:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventTaskVo {
    /**
     * <pre>
     * 任务名称
     * </pre>
     */
    private String	taskName;

    /**
     * <pre>
     * 任务标识，小写英文，数字 "_"连接
     * </pre>
     */
    private String taskAliasName;

    /**
     * <pre>
     * 拼接SQL元数据
     * </pre>
     */
    private EventTaskSqlMetaDataVo sqlMetaData;

    /**
     * <pre>
     * 创建表sql
     * </pre>
     */
    private String createTableSql;

    private String deleteTaskSql;

    /**
     * <pre>
     * 插入sql
     * </pre>
     */
    private String insertTaskSql;

    /**
     * <pre>
     * 查询sql
     * </pre>
     */
    private String selectTaskSql;
}