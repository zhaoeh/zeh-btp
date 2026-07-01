package com.ai.controller;

import com.ai.dto.AiResponse;
import com.ai.dto.ChatRequest;
import com.ai.dto.PromptTemplateRequest;
import com.ai.dto.SentimentResult;
import com.ai.service.AiDemoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 面向学习的 Spring AI 完整案例入口。
 * Controller 只负责 HTTP 协议与参数校验，AI 编排集中在 {@link AiDemoService}，便于观察分层职责。
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai/learn")
public class AiLearningController {

    private final AiDemoService aiDemoService;

    /**
     * 调用带 System Prompt 和响应元数据的基础对话案例。
     *
     * @param request 已通过 Bean Validation 的用户消息
     * @return 回答正文、模型名称和 token 用量
     */
    @PostMapping("/chat")
    public AiResponse chat(@Valid @RequestBody ChatRequest request) {
        return aiDemoService.chat(request.message());
    }

    /**
     * 调用 Prompt Template 变量填充案例。
     *
     * @param request 主题、受众和输出语言
     * @return 模型回答及调用元数据
     */
    @PostMapping("/prompt-template")
    public AiResponse promptTemplate(@Valid @RequestBody PromptTemplateRequest request) {
        return aiDemoService.promptTemplate(request);
    }

    /**
     * 调用 Structured Output 案例，把模型文本转换为 Java record。
     *
     * @param request 待分析文本
     * @return 经过基础业务校验的结构化情感结果
     */
    @PostMapping("/structured-output")
    public SentimentResult structuredOutput(@Valid @RequestBody ChatRequest request) {
        return aiDemoService.structuredOutput(request.message());
    }

    /**
     * 调用带窗口记忆的多轮对话案例。
     *
     * @param conversationId 会话隔离键；示例限制为 1 至 64 位安全字符
     * @param request 当前轮用户消息
     * @return 结合对应会话历史生成的回答
     */
    @PostMapping("/memory/{conversationId}")
    public AiResponse memory(
            @PathVariable @Pattern(regexp = "[a-zA-Z0-9_-]{1,64}", message = "conversationId格式不正确")
            String conversationId,
            @Valid @RequestBody ChatRequest request) {
        return aiDemoService.memoryChat(conversationId, request.message());
    }

    /**
     * 删除指定会话的全部内存消息。
     *
     * @param conversationId 要清理的会话隔离键
     * @return 204 No Content
     */
    @DeleteMapping("/memory/{conversationId}")
    public ResponseEntity<Void> clearMemory(
            @PathVariable @Pattern(regexp = "[a-zA-Z0-9_-]{1,64}", message = "conversationId格式不正确")
            String conversationId) {
        aiDemoService.clearMemory(conversationId);
        return ResponseEntity.noContent().build();
    }
}
