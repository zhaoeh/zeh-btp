package jp.co.futech.module.insight.bean;

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
public class WhereTemplate {

    /**
     * <pre>
     * 查询字段
     * </pre>
     */
    private String	column;

    /**
     * <pre>
     * 标识
     * </pre>
     */
    private String symbol;

    /**
     * <pre>
     * 入参
     * </pre>
     */
    private String	input;

    /**
     * <pre>
     * and 或者 or
     * </pre>
     */
    private String	relation;

    /**
     * <pre>
     * 字段类型
     * </pre>
     */
    private String	colType;

    /**
     * <pre>
     * 主表的日期查询条件
     * </pre>
     */
    private String	mainDate;

}