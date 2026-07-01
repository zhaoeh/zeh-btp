package com.ai.dto;

/**
 * RAG 召回文档的来源视图。
 *
 * @param id Document 唯一标识
 * @param source 原始知识来源，如内置知识或 classpath 文件
 * @param category 业务分类元数据，可进一步用于过滤检索
 * @param score 查询与文档的向量相似度；由 VectorStore 返回
 * @param excerpt 文档内容截断摘要，避免接口返回整个知识块
 */
public record RagSource(String id, String source, String category, Double score, String excerpt) {
}
