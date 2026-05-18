package com.ai.dto;

import lombok.Data;

import java.util.List;

@Data
public class SqlAnalysisResult {

    /**
     * 风险等级
     */
    private String riskLevel;

    /**
     * 是否全表扫描
     */
    private Boolean fullTableScan;

    /**
     * 是否存在 select *
     */
    private Boolean selectStar;

    /**
     * 是否存在深分页
     */
    private Boolean deepPagination;

    /**
     * 优化建议
     */
    private List<String> suggestions;
}