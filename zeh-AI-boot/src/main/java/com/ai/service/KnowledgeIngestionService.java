package com.ai.service;

import com.ai.dto.KnowledgeLoadResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * RAG ETL 的编排入口：Reader -> Transformer -> EmbeddingModel -> VectorStore。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KnowledgeIngestionService {

    private final KnowledgeService knowledgeService;
    private final PdfKnowledgeService pdfKnowledgeService;
    private final VectorStore vectorStore;

    @Value("${app.ai.knowledge.initialize-on-startup:false}")
    private boolean initializeOnStartup;

    private List<String> loadedDocumentIds = List.of();

    @EventListener(ApplicationReadyEvent.class)
    public void initializeAfterApplicationReady() {
        if (!initializeOnStartup) {
            log.info("Knowledge initialization skipped; call POST /rag/knowledge/reload when Ollama is ready");
            return;
        }
        try {
            reload();
        }
        catch (Exception exception) {
            // AI 基础设施暂时不可用，不应让整个微服务启动失败。
            log.warn("Knowledge initialization failed; manual reload is still available: {}", exception.getMessage());
        }
    }

    public synchronized KnowledgeLoadResult reload() throws Exception {
        List<Document> documents = new ArrayList<>(knowledgeService.loadBuiltInKnowledge());
        documents.addAll(pdfKnowledgeService.loadResource());

        if (!loadedDocumentIds.isEmpty()) {
            vectorStore.delete(loadedDocumentIds);
        }
        vectorStore.add(documents);
        loadedDocumentIds = documents.stream().map(Document::getId).toList();

        log.info("Knowledge loaded: documentCount={}", documents.size());
        return new KnowledgeLoadResult(documents.size(), vectorStore.getName(), "READY");
    }
}
