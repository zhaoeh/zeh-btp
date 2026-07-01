package com.ai.dto;

/**
 * 一次知识入库的摘要。
 *
 * @param documentCount 最终写入 VectorStore 的 Document/Chunk 数量
 * @param vectorStore 当前向量存储实现名称
 * @param status 入库状态；成功时为 READY
 */
public record KnowledgeLoadResult(int documentCount, String vectorStore, String status) {
}
