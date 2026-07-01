package com.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Prompt Template 示例参数。
 *
 * @param topic 希望讲解的主题
 * @param audience 目标受众，用于调整表达深度
 * @param language 期望输出语言
 */
public record PromptTemplateRequest(
        @NotBlank(message = "topic不能为空") @Size(max = 200) String topic,
        @NotBlank(message = "audience不能为空") @Size(max = 100) String audience,
        @NotBlank(message = "language不能为空") @Size(max = 30) String language) {
}
