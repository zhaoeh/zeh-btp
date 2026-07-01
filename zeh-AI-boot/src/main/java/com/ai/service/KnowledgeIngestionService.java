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

    /**
     * 应用完全启动后按配置决定是否自动入库。
     * 使用 ApplicationReadyEvent 而非 PostConstruct，避免在 Bean 创建阶段访问外部 Embedding 服务。
     */
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

    /**
     * 重新执行知识读取、切片、向量化和写入。
     * synchronized 防止多个 HTTP 请求并发重载同一个内存 VectorStore。
     *
     * @return 本次成功写入的文档统计
     * @throws Exception classpath 资源读取、Embedding 模型调用或向量存储操作失败
     */
    public synchronized KnowledgeLoadResult reload() throws Exception {
        // Reader：把代码内置知识转换为 Document；ArrayList 用于继续合并文件切片。
        List<Document> documents = new ArrayList<>(knowledgeService.loadBuiltInKnowledge());
        // Reader + Transformer：读取 classpath 文本并使用 TokenTextSplitter 切成多个 Document。
        documents.addAll(pdfKnowledgeService.loadResource());

        if (!loadedDocumentIds.isEmpty()) {
            // 先删除上次由本服务写入的 ID，避免同一进程内重复 reload 造成重复召回。
            vectorStore.delete(loadedDocumentIds);
        }
        // Writer：add 内部先调用 EmbeddingModel 生成向量，再把 Document 与向量写入 VectorStore。
        vectorStore.add(documents);
        // 保存本轮 ID，仅用于后续 reload 清理；SimpleVectorStore 重启后本身也会清空。
        loadedDocumentIds = documents.stream().map(Document::getId).toList();

        log.info("Knowledge loaded: documentCount={}", documents.size());
        return new KnowledgeLoadResult(documents.size(), vectorStore.getName(), "READY");
    }
}
