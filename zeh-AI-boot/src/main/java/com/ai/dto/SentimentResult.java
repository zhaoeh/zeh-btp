package com.ai.dto;

import java.util.List;

/**
 * entity(Class) 会把模型输出映射为这个结构，展示 Structured Output 的使用方式。
 *
 * @param sentiment 模型判断的情感类别
 * @param confidence 置信度，业务层进一步约束在 0 到 1 之间
 * @param summary 输入文本的简要概括
 * @param keywords 从输入中提取的关键词
 */
public record SentimentResult(
        String sentiment,
        double confidence,
        String summary,
        List<String> keywords) {
}
