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
public class EventTaskFiltersColVo {
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
     * # 数据类型：1-String 2-Int64 3-Date 4-Decimal(38, 6)
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
     * #1 >，2 <，3 = ，4 ！=， 5 >=，6 <= ,7 为空， 8 不为空，9 包含， 10 不包含
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
     *     是否是自定义指标
     * </pre>
     */
    private String isCustomer;

    /**
     * <pre>
     *     自定义指标的计算公式
     * </pre>
     */
    private String customerCalculation;

    /**
     * <pre>
     * 此处的第一个筛选条件关系为and固定，后面的可以为or
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