package com.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PromptTemplateRequest(
        @NotBlank(message = "topic不能为空") @Size(max = 200) String topic,
        @NotBlank(message = "audience不能为空") @Size(max = 100) String audience,
        @NotBlank(message = "language不能为空") @Size(max = 30) String language) {
}
