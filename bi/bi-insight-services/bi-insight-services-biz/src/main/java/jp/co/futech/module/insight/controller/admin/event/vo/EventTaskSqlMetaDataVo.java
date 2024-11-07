package jp.co.futech.module.insight.controller.admin.event.vo;

import lombok.Data;

import java.util.List;

/**
 * <pre>
 *  FiltersCol
 * </pre>
 * @author zh
 * @verison $Id: FiltersCol v 0.1 2024-07-05 12:30:07
 */
@Data
public class EventTaskSqlMetaDataVo {

    /**
     * <pre>
     * calcSql
     * </pre>
     */
    private List<EventTaskCalcSqlVo> calcSql;

    /**
     * <pre>
     * filterData
     * </pre>
     */
    private EventTaskFilterDataVo filterData;

    /**
     * <pre>
     * dimension
     * </pre>
     */
    private List<EventTaskDimensionVo>	dimension;
    /**
     * 复合计算的字段
     */
    private List<EventTaskComSql> comSql;

}