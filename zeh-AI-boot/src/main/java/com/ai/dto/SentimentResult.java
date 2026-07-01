package com.ai.dto;

import java.util.List;

/**
 * entity(Class) 会把模型输出映射为这个结构，展示 Structured Output 的使用方式。
 */
public record SentimentResult(
        String sentiment,
        double confidence,
        String summary,
        List<String> keywords) {
}
