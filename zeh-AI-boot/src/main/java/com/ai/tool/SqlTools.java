package com.ai.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class SqlTools {

    @Tool(description = "获取SQL执行计划")
    public String explain(String sql) {

        return """
                id:1
                type:ALL
                table:user
                possible_keys:null
                key:null
                rows:100000
                Extra:Using where
                """;
    }

}