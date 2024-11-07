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
public class EventTaskFiltersVo {
    /**
     * <pre>
     *     guid
     * </pre>
     */
    private String guid;


    /**
     * <pre>
     *
     * </pre>
     */
    private String	filterColName;

    /**
     * <pre>
     *
     * </pre>
     */
    private String	filterColRename;

    /**
     * <pre>
     * 数据类型：1-String 2-Int64 3-Date 4-Decimal(38, 6)
     * </pre>
     */
    private String	filterColType;

    /**
     * <pre>
     *
     * </pre>
     */
    private String	filterColTableName;

    /**
     * <pre>
     * 数据类型：1-String 2-Int64 3-Date 4-Decimal(38, 6)
     * </pre>
     */
    private String	filterSymbol;

    /**
     * <pre>
     *
     * </pre>
     */
    private String	filterContent;

    /**
     * <pre>
     * 此处的第一个筛选条件可以为空,按顺序往下，看下面的这个字段到底是and还是or
     * </pre>
     */
    private String	beforeRelation;

    /**
     * <pre>
     * 子查询表关联，获取对应的loginName字段
     * </pre>
     */
    private String relationColumn;
    /**
     * <pre>
     * Date字段
     * </pre>
     */
    private String dateColumn;
}