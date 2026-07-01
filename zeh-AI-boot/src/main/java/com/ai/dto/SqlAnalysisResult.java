package com.ai.dto;

import lombok.Data;

import java.util.List;

/**
 * SQL Agent 的 Structured Output 目标类型。
 * Lombok {@link Data} 生成 getter/setter，使 BeanOutputConverter/Jackson 可以完成对象映射。
 */
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
