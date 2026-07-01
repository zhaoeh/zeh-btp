package com.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 通用单消息请求。
 *
 * @param message 用户输入；限制长度可避免空调用和失控的上下文/token 消耗
 */
public record ChatRequest(
        @NotBlank(message = "message不能为空")
        @Size(max = 4000, message = "message不能超过4000个字符")
        String message) {
}
