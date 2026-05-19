package com.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    /**
     * 注册LLM模型体系中最核心的客户端会话对象
     *
     * @param builder LLM会话客户端构建器
     * @return LLM会话客户端实例对象
     */
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }


    /**
     * 构建向量存储实例对象
     * @param embeddingModel embeddingModel模型实例，EmbeddingModel模型专门负责文本向量化
     * @return 通过embeddingModel模型实例构建的向量存储实例对象，负责操作向量数据库
     */
    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel)
                .build();
    }
}
