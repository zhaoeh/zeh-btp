package com.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KnowledgeService {

    private final VectorStore vectorStore;

    public void init() {

        // 构建文档对象，手动构建，模拟两个文档，添加到本地向量库
        List<Document> documents = List.of(

                new Document("""
                        提现失败原因：
                        
                        1. 银行卡异常
                        2. 风控审核失败
                        3. 账户余额不足
                        """),

                new Document("""
                        提现到账时间：
                        
                        正常情况下5分钟到账。
                        高峰期可能延迟30分钟。
                        """)
        );

        // add方法：将AI文档对象添加到本地向量库，内部流程大致如下：
        // 1.调 embedding 模型
        // 2. 文本转 vector
        // 3. 存入本地向量数据库
        // 上述3个步骤全部是spring ai帮忙自动实现调度的
        vectorStore.add(documents);
    }
}