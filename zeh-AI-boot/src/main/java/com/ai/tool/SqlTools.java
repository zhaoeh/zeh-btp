package com.ai.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

/**
 * SQL Agent 可调用的工具集合。
 * 当前返回固定 EXPLAIN 结果用于学习 Tool Calling，并未连接真实数据库。
 */
@Service
public class SqlTools {

    /**
     * 模拟执行只读 SQL 的 EXPLAIN。
     * {@link Tool} 描述帮助模型判断何时调用；{@link ToolParam} 描述帮助模型生成参数。
     *
     * @param sql 模型传入的单条 SELECT
     * @return 模拟的 MySQL 执行计划文本，随后由模型继续解释
     * @throws IllegalArgumentException 参数不是受限的单条只读 SELECT
     */
    @Tool(description = "获取SQL执行计划")
    public String explain(@ToolParam(description = "只允许单条SELECT语句") String sql) {

        // 规范化仅用于演示级安全判断；真实系统必须使用 SQL Parser、只读账号和数据库权限共同防护。
        String normalized = sql == null ? "" : sql.strip().toLowerCase();
        if (!normalized.startsWith("select ") || normalized.contains(";")
                || normalized.matches("(?s).*(insert|update|delete|drop|alter|truncate)\\s+.*")) {
            throw new IllegalArgumentException("演示工具只允许不带分号的单条SELECT语句");
        }

        // 工具结果不会直接返回 HTTP，而是作为 ToolMessage 交回模型生成最终结构化结论。
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
