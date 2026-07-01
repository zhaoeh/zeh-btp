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

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai/learn")
public class AiLearningController {

    private final AiDemoService aiDemoService;

    @PostMapping("/chat")
    public AiResponse chat(@Valid @RequestBody ChatRequest request) {
        return aiDemoService.chat(request.message());
    }

    @PostMapping("/prompt-template")
    public AiResponse promptTemplate(@Valid @RequestBody PromptTemplateRequest request) {
        return aiDemoService.promptTemplate(request);
    }

    @PostMapping("/structured-output")
    public SentimentResult structuredOutput(@Valid @RequestBody ChatRequest request) {
        return aiDemoService.structuredOutput(request.message());
    }

    @PostMapping("/memory/{conversationId}")
    public AiResponse memory(
            @PathVariable @Pattern(regexp = "[a-zA-Z0-9_-]{1,64}", message = "conversationId格式不正确")
            String conversationId,
            @Valid @RequestBody ChatRequest request) {
        return aiDemoService.memoryChat(conversationId, request.message());
    }

    @DeleteMapping("/memory/{conversationId}")
    public ResponseEntity<Void> clearMemory(
            @PathVariable @Pattern(regexp = "[a-zA-Z0-9_-]{1,64}", message = "conversationId格式不正确")
            String conversationId) {
        aiDemoService.clearMemory(conversationId);
        return ResponseEntity.noContent().build();
    }
}
