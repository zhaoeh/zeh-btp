package com.ai.service;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KnowledgeService {

    /** Reader 阶段：只负责把业务知识转换为带元数据的 Document。 */
    public List<Document> loadBuiltInKnowledge() {
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
