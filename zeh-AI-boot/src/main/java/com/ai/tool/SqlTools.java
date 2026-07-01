package com.ai.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class SqlTools {

    @Tool(description = "获取SQL执行计划")
    public String explain(@ToolParam(description = "只允许单条SELECT语句") String sql) {

        String normalized = sql == null ? "" : sql.strip().toLowerCase();
        if (!normalized.startsWith("select ") || normalized.contains(";")
                || normalized.matches("(?s).*(insert|update|delete|drop|alter|truncate)\\s+.*")) {
            throw new IllegalArgumentException("演示工具只允许不带分号的单条SELECT语句");
        }

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
