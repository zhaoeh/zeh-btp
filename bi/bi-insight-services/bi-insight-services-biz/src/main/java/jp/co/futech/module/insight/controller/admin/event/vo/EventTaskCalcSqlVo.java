package jp.co.futech.module.insight.controller.admin.event.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  FiltersCol
 * </pre>
 * @author zh
 * @verison $Id: FiltersCol v 0.1 2024-07-05 12:30:07
 */
@Data
public class EventTaskCalcSqlVo {
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
    private String	colName;

    /**
     * <pre>
     *
     * </pre>
     */
    private String	colRename;

    /**
     * <pre>
     *
     * </pre>
     */
    private String	colTableName;

    /**
     * <pre>
     * # 数据类型：1-String 2-Int64 3-Date 4-Decimal(38, 6)
     * </pre>
     */
    private String	colType;

    /**
     * <pre>
     *
     * </pre>
     */
    private String	colCalculation;

    /**
     * <pre>
     * 相对任务执行时间减多少天
     * </pre>
     */
    private String	minuDts;

    /**
     * <pre>
     * 是否是用户自定义指标，0 否，1 是
     * </pre>
     */
    private String	isCustomer;

    /**
     * <pre>
     * 用户自定义指标的计算逻辑，不是自定义指标为空
     * </pre>
     */
    private String customerCalculation;

    /**
     * <pre>
     * filtersCol
     * </pre>
     */
    private List<EventTaskFiltersColVo> filtersCol;

    private Map<String,EventTaskFiltersColVo> filtersColMap;

    /**
     * <pre>
     * 子查询表关联，获取对应的loginName字段
     * </pre>
     */
    private String relationColumn;
    /**
     * <pre>
     * 主表的Date字段
     * </pre>
     */
    private String dateColumn;
    /**
     * <pre>
     * 别名
     * </pre>
     */
    private String eventAliasName;
}