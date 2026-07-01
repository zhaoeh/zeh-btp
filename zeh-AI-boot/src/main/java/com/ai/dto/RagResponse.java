package com.ai.dto;

import java.util.List;

/**
 * 可追溯的 RAG 最终响应。
 *
 * @param answer LLM 基于召回上下文生成的答案；无有效召回时为固定拒答
 * @param sources 本次真正参与生成的召回文档，便于引用展示和检索调试
 */
public record RagResponse(String answer, List<RagSource> sources) {
}
