package com.ai.controller;

import com.ai.dto.SqlAnalysisResult;
import com.ai.service.SQLAgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sql")
public class SQLController {

    private final SQLAgentService sqlAgentService;

    @PostMapping("/analyze")
    public SqlAnalysisResult analyze(@RequestBody String sql) {
        return sqlAgentService.analyze(sql);
    }
}
