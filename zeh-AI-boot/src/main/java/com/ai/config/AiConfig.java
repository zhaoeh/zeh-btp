package com.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring AI 核心对象配置。
 *
 * <p>模型实现由 {@code spring-ai-starter-model-ollama} 根据 application.yml 自动配置；
 * 本类在自动配置产物之上组装应用层使用的 ChatClient、ChatMemory 和 VectorStore。</p>
 */
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
        // Builder 是 Spring AI 自动配置的 prototype Bean，已关联 OllamaChatModel、观测能力等组件。
        // build() 得到线程安全、可复用的 ChatClient；每次 prompt() 才会创建一次请求规格。
        return builder.build();
    }

    /**
     * 模型接口本身是无状态的。这个 Bean 保存最近 20 条消息，
     * 再由 MessageChatMemoryAdvisor 在每次请求前后读写。
     * 生产环境应把默认内存仓库替换成 JDBC/Redis 等持久化实现。
     */
    @Bean
    public ChatMemory chatMemory() {
        // MessageWindowChatMemory 只保存消息窗口，不会让模型本身变成有状态。
        // maxMessages 控制送回模型的历史规模，避免对话无限增长并持续消耗 token。
        return MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
    }


    /**
     * 构建向量存储实例对象
     * @param embeddingModel embeddingModel模型实例，EmbeddingModel模型专门负责文本向量化
     * @return 通过embeddingModel模型实例构建的向量存储实例对象，负责操作向量数据库
     */
    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        // SimpleVectorStore 在 add/search 时调用注入的 EmbeddingModel 完成文本向量化和查询向量化。
        // 它仅驻留内存，适合教学；应用重启后数据会丢失。
        return SimpleVectorStore.builder(embeddingModel)
                .build();
    }
}
