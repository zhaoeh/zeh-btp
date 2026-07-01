package com.ai.controller;

import com.ai.dto.KnowledgeLoadResult;
import com.ai.dto.RagResponse;
import com.ai.service.KnowledgeIngestionService;
import com.ai.service.RagService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/rag")
public class RagController {

    private final RagService ragService;

    private final KnowledgeIngestionService knowledgeIngestionService;

    @GetMapping("/chat")
    public String chat(@NotBlank @Size(max = 1000) String q) {
        return ragService.chat(q);
    }

    @GetMapping("/ask")
    public RagResponse ask(@NotBlank @Size(max = 1000) String q) {
        return ragService.ask(q);
    }

    @PostMapping("/knowledge/reload")
    public KnowledgeLoadResult reload() throws Exception {
        return knowledgeIngestionService.reload();
    }
}
