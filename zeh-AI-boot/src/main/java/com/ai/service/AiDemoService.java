package com.ai.service;

import com.ai.advisor.AiLifecycleLoggerAdvisor;
import com.ai.dto.AiResponse;
import com.ai.dto.PromptTemplateRequest;
import com.ai.dto.SentimentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiDemoService {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;
    private final AiLifecycleLoggerAdvisor lifecycleLoggerAdvisor;

    /** 基础调用，同时返回模型和 token 元数据，而不只是一个字符串。 */
    public AiResponse chat(String message) {
        ChatResponse response = chatClient.prompt()
                .system("你是一个严谨的中文技术助手。不确定时明确说不知道，不编造事实。")
                .user(message)
                .advisors(lifecycleLoggerAdvisor)
                .call()
                .chatResponse();
        return AiResponse.from(response);
    }

    /** 使用变量而不是字符串拼接，演示 Prompt Template 的构建阶段。 */
    public AiResponse promptTemplate(PromptTemplateRequest request) {
        ChatResponse response = chatClient.prompt()
                .system("你是一名擅长因材施教的讲师。")
                .user(user -> user
                        .text("请使用{language}，为{audience}讲解{topic}。给出概念、流程和一个例子。")
                        .param("language", request.language())
                        .param("audience", request.audience())
                        .param("topic", request.topic()))
                .advisors(lifecycleLoggerAdvisor)
                .call()
                .chatResponse();
        return AiResponse.from(response);
    }

    /** 让 Spring AI 注入格式约束并把模型文本映射为 Java 对象。 */
    public SentimentResult structuredOutput(String text) {
        SentimentResult result = chatClient.prompt()
                .system("你是文本分析器。只依据输入文本判断，不补充输入中没有的信息。")
                .user(user -> user
                        .text("分析下面文本的情感、置信度、摘要和关键词：\n{text}")
                        .param("text", text))
                .advisors(lifecycleLoggerAdvisor)
                .call()
                .entity(SentimentResult.class);
        if (result == null) {
            throw new IllegalStateException("结构化输出转换失败");
        }
        if (result.sentiment() == null || result.sentiment().isBlank()
                || result.confidence() < 0 || result.confidence() > 1
                || result.summary() == null || result.keywords() == null) {
            throw new IllegalStateException("模型返回的结构化字段不符合业务约束");
        }
        return result;
    }

    /**
     * 每次仍然只发起一个无状态模型调用；Advisor 在调用前读取历史，调用后写回历史。
     */
    public AiResponse memoryChat(String conversationId, String message) {
        MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        ChatResponse response = chatClient.prompt()
                .system("你是一个能结合当前会话历史回答问题的中文助手。")
                .user(message)
                .advisors(advisor -> advisor
                        .advisors(memoryAdvisor, lifecycleLoggerAdvisor)
                        .param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .chatResponse();
        return AiResponse.from(response);
    }

    public void clearMemory(String conversationId) {
        chatMemory.clear(conversationId);
    }
}
