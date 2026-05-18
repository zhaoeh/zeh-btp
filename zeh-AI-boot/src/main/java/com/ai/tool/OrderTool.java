package com.ai.tool;

import org.springframework.ai.tool.annotation.Tool;
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
     * @param id 入参
     * @return 响应
     */
    @Tool(description = "根据订单ID查询订单信息")
    public String getOrder(Long id) {

        return """
                订单信息：
                
                订单ID：%s
                用户：张三
                金额：998
                状态：已支付
                """.formatted(id);
    }



    @Tool(description = "根据用户ID查询余额")
    public String getBalance(Long userId) {

        return """
            用户余额：
            
            用户ID：%s
            余额：8888
            """.formatted(userId);
    }
}
