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
public class EventTaskFilterDataVo {
    /**
     * <pre>
     * filters
     * </pre>
     */
    private List<EventTaskFiltersVo> filters;
}