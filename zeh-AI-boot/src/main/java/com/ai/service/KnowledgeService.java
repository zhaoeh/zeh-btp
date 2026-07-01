package com.ai.service;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 代码内置知识的 DocumentReader 示例。
 * 只负责构造 Spring AI Document，不负责 Embedding 或写入 VectorStore。
 */
@Service
public class KnowledgeService {

    /**
     * Reader 阶段：把两段提现知识转换为带稳定 ID 和元数据的 Document。
     * Document 文本用于 Embedding 和最终上下文，metadata 用于过滤、引用和追溯。
     *
     * @return 尚未向量化的内置知识文档
     */
    public List<Document> loadBuiltInKnowledge() {
        // 显式 ID 使重复入库行为可控；source/category 必须使用向量库普遍支持的简单值类型。
        return List.of(

                new Document("withdraw-failure", """
                        提现失败原因：
                        
                        1. 银行卡异常
                        2. 风控审核失败
                        3. 账户余额不足
                        """, Map.of("source", "built-in", "category", "failure")),

                new Document("withdraw-arrival", """
                        提现到账时间：
                        
                        正常情况下5分钟到账。
                        高峰期可能延迟30分钟。
                        """, Map.of("source", "built-in", "category", "arrival"))
        );
    }
}
