package com.ai.dto;

import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;

public record AiResponse(
        String content,
        String model,
        Integer promptTokens,
        Integer completionTokens,
        Integer totalTokens) {

    public static AiResponse from(ChatResponse response) {
        if (response == null || response.getResult() == null) {
            throw new IllegalStateException("模型没有返回可用结果");
        }
        Usage usage = response.getMetadata().getUsage();
        return new AiResponse(
                response.getResult().getOutput().getText(),
                response.getMetadata().getModel(),
                usage.getPromptTokens(),
                usage.getCompletionTokens(),
                usage.getTotalTokens());
    }
}
