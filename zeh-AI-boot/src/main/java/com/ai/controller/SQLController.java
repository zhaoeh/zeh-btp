package com.ai.controller;

import com.ai.dto.SqlAnalysisResult;
import com.ai.service.SQLAgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SQL 智能分析 Demo 的 HTTP 适配层。
 * 原始 SQL 直接交由 {@link SQLAgentService} 完成 Prompt、Tool Calling 和结构化输出编排。
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sql")
public class SQLController {

    private final SQLAgentService sqlAgentService;

    /**
     * 使用 DBA 角色提示词和模拟 EXPLAIN 工具分析一段 SQL。
     *
     * @param sql text/plain 请求体中的 SQL
     * @return 模型映射后的结构化风险分析
     */
    @PostMapping("/analyze")
    public SqlAnalysisResult analyze(@RequestBody String sql) {
        return sqlAgentService.analyze(sql);
    }
}
