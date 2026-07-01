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

/**
 * RAG（检索增强生成）HTTP 入口。
 * 同时暴露兼容旧调用的纯文本接口、包含引用的新接口，以及手动知识入库接口。
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/rag")
public class RagController {

    private final RagService ragService;

    private final KnowledgeIngestionService knowledgeIngestionService;

    /**
     * 兼容原始 Demo 的简化问答接口，只返回回答文本。
     *
     * @param q 用户问题；方法级校验由 {@link Validated} 启用
     * @return RAG 最终回答
     */
    @GetMapping("/chat")
    public String chat(@NotBlank @Size(max = 1000) String q) {
        return ragService.chat(q);
    }

    /**
     * 完整 RAG 问答接口，除答案外还返回实际召回的知识来源。
     *
     * @param q 用户问题
     * @return 答案和可追溯引用列表
     */
    @GetMapping("/ask")
    public RagResponse ask(@NotBlank @Size(max = 1000) String q) {
        return ragService.ask(q);
    }

    /**
     * 手动重新执行知识 ETL 和向量入库。
     * 默认启动配置不自动连接 Embedding 模型，因此首次 RAG 前需调用本接口。
     *
     * @return 本次载入文档数、向量库名称及状态
     * @throws Exception 资源读取或 Embedding/VectorStore 写入失败
     */
    @PostMapping("/knowledge/reload")
    public KnowledgeLoadResult reload() throws Exception {
        return knowledgeIngestionService.reload();
    }
}
