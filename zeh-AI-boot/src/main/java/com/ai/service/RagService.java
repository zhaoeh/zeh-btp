package com.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RAG服务：真正核心，专门负责查询向量数据库
 */
@Service
@RequiredArgsConstructor
public class RagService {

    private final VectorStore vectorStore;

    private final ChatClient chatClient;

    /**
     * RAG全流程：
     * 1.用户提问：提现不到账怎么办
     * 2.向量搜索：similaritySearch()，找到最相关知识
     * 3.拼接context：向context上下文拼接各个因子
     * 4.投喂给LLM大模型进行调度
     * @param question
     * @return
     */
    public String chat(String question) {

        List<Document> documents = vectorStore.similaritySearch(question);

        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));

        String prompt = """
                请基于以下知识回答问题。
                
                知识：
                
                %s
                
                用户问题：
                
                %s
                """.formatted(context, question);

        return chatClient.prompt(prompt)
                .call()
                .content();
    }
}