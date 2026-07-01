package com.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChatRequest(
        @NotBlank(message = "message不能为空")
        @Size(max = 4000, message = "message不能超过4000个字符")
        String message) {
}
