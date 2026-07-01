package com.ai.dto;

import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;

/**
 * 对 ChatResponse 的教学型精简视图。
 *
 * @param content 第一条模型 Generation 的文本
 * @param model 实际处理请求的模型名称
 * @param promptTokens 输入消息消耗的 token 数
 * @param completionTokens 模型输出消耗的 token 数
 * @param totalTokens 输入与输出 token 总数
 */
public record AiResponse(
        String content,
        String model,
        Integer promptTokens,
        Integer completionTokens,
        Integer totalTokens) {

    /**
     * 从 Spring AI 原始响应中提取业务常用字段。
     * ChatResponse 还能携带多候选 Generation、限流和提供商原生元数据，本案例仅选择第一条结果。
     *
     * @param response ChatModel 返回的完整响应
     * @return 适合 REST 输出的精简对象
     * @throws IllegalStateException 模型未返回任何 Generation
     */
    public static AiResponse from(ChatResponse response) {
        if (response == null || response.getResult() == null) {
            throw new IllegalStateException("模型没有返回可用结果");
        }
        // Usage 是跨模型提供商的统一抽象，屏蔽 Ollama/OpenAI 等原始响应字段差异。
        Usage usage = response.getMetadata().getUsage();
        return new AiResponse(
                response.getResult().getOutput().getText(),
                response.getMetadata().getModel(),
                usage.getPromptTokens(),
                usage.getCompletionTokens(),
                usage.getTotalTokens());
    }
}
