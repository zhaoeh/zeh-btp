package com.ai.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * AI tool calling：本质就是LLM会自动调度应用提供的一堆tool
 * 应用提供的tool就是一个方法，很简单
 * LLM会对用户输入的prompt进行分析，自动推理，然后根据内部模型推理结果去编排他认为合理的tools的调度链路
 * 因此，自动编排链路，本身就是if else等逻辑，逻辑本身就表示了人工智能推理的一个过程，不用再需要手动去写if else了，而是模型会自动进行推理
 */
@Component
public class OrderTool {

    /**
     * 注解@Tool表示将这个api作为一个tool暴露给AI，以供其调用
     *
     * @param id 模型根据用户问题抽取的订单 ID
     * @return 会作为 ToolMessage 回传给模型的订单信息
     */
    @Tool(description = "根据订单ID查询订单信息")
    public String getOrder(@ToolParam(description = "大于0的订单ID") Long id) {

        requirePositive(id, "订单ID");

        return """
                订单信息：
                
                订单ID：%s
                用户：张三
                金额：998
                状态：已支付
                """.formatted(id);
    }

    /**
     * 查询用户余额的演示工具。
     * {@link ToolParam} 的说明会进入工具参数 JSON Schema，帮助模型正确构造调用参数。
     *
     * @param userId 模型根据用户问题抽取的用户 ID
     * @return 会作为 ToolMessage 回传给模型的余额信息
     */
    @Tool(description = "根据用户ID查询余额")
    public String getBalance(@ToolParam(description = "大于0的用户ID") Long userId) {

        requirePositive(userId, "用户ID");

        return """
            用户余额：
            
            用户ID：%s
            余额：8888
            """.formatted(userId);
    }

    /**
     * 工具边界上的参数校验。模型生成的参数仍属于不可信输入，不能跳过业务校验。
     *
     * @param value 待校验 ID
     * @param fieldName 错误消息使用的业务字段名
     * @throws IllegalArgumentException ID 为空或非正数
     */
    private void requirePositive(Long value, String fieldName) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(fieldName + "必须大于0");
        }
    }
}
