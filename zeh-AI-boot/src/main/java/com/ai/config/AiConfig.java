package com.ai.config;

import org.springframework.ai.chat.client.ChatClient;
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
}
