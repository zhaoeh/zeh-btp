package jp.co.futech.module.insight.controller.admin.event.vo;

import lombok.Data;

/**
 * <pre>
 *  FiltersCol
 * </pre>
 * @author zh
 * @verison $Id: FiltersCol v 0.1 2024-07-05 12:30:07
 */
@Data
public class EventTaskDimensionVo {
    /**
     * <pre>
     *     guid
     * </pre>
     */
    private String guid;

    /**
     * <pre>
     * 维度的列名
     * </pre>
     */
    private String	colName;

    /**
     * <pre>
     *
     * </pre>
     */
    private String	colRename;

    /**
     * <pre>
     * 维度所属的表名
     * </pre>
     */
    private String	colTableName;

    /**
     * <pre>
     * 数据类型：1-String 2-Int64 3-Date 4-Decimal(38, 6)
     * </pre>
     */
    private Integer	dataType;

    /**
     * <pre>
     * 子查询表关联，获取对应的loginName字段
     * </pre>
     */
    private String relationColumn;

}