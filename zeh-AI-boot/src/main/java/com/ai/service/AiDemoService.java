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

/**
 * Spring AI 核心 API 学习服务。
 * 每个方法聚焦一个概念：完整响应、Prompt Template、Structured Output 或 Chat Memory。
 */
@Service
@RequiredArgsConstructor
public class AiDemoService {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;
    private final AiLifecycleLoggerAdvisor lifecycleLoggerAdvisor;

    /**
     * 基础调用，同时返回模型和 token 元数据，而不只是一个字符串。
     *
     * @param message 当前用户问题
     * @return 从 ChatResponse 提取的回答与 usage
     */
    public AiResponse chat(String message) {
        // prompt() 创建空请求规格，后续可分别添加不同角色的 Message。
        ChatResponse response = chatClient.prompt()
                // system() 生成 SystemMessage，定义模型在本次调用中的最高级行为约束。
                .system("你是一个严谨的中文技术助手。不确定时明确说不知道，不编造事实。")
                // user() 生成 UserMessage，承载当前用户任务。
                .user(message)
                .advisors(lifecycleLoggerAdvisor)
                // call() 发起同步调用；chatResponse() 保留文本之外的模型、token、完成原因等信息。
                .call()
                .chatResponse();
        return AiResponse.from(response);
    }

    /**
     * 使用变量而不是字符串拼接，演示 Prompt Template 的构建阶段。
     *
     * @param request 模板变量集合
     * @return 模板渲染后模型生成的回答与 usage
     */
    public AiResponse promptTemplate(PromptTemplateRequest request) {
        ChatResponse response = chatClient.prompt()
                .system("你是一名擅长因材施教的讲师。")
                // Consumer<UserSpec> 允许同时定义模板文本与多项变量。
                .user(user -> user
                        // 花括号是默认 StringTemplate 渲染器的占位符，并非直接发送给模型。
                        .text("请使用{language}，为{audience}讲解{topic}。给出概念、流程和一个例子。")
                        // param() 在 Prompt 创建阶段替换同名占位符；比手工字符串拼接更易复用和审查。
                        .param("language", request.language())
                        .param("audience", request.audience())
                        .param("topic", request.topic()))
                .advisors(lifecycleLoggerAdvisor)
                .call()
                .chatResponse();
        return AiResponse.from(response);
    }

    /**
     * 让 Spring AI 注入格式约束并把模型文本映射为 Java 对象。
     *
     * @param text 待做情感分析的原始文本
     * @return 转换并经过业务字段校验的 SentimentResult
     * @throws IllegalStateException 模型无结果或生成内容不符合结构/范围约束
     */
    public SentimentResult structuredOutput(String text) {
        SentimentResult result = chatClient.prompt()
                .system("你是文本分析器。只依据输入文本判断，不补充输入中没有的信息。")
                .user(user -> user
                        .text("分析下面文本的情感、置信度、摘要和关键词：\n{text}")
                        .param("text", text))
                .advisors(lifecycleLoggerAdvisor)
                .call()
                // entity(Class)：内部使用 BeanOutputConverter 生成格式说明，并把模型 JSON 映射为目标 record。
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
     *
     * @param conversationId 会话隔离键，用于在同一个 ChatMemory 中区分多段历史
     * @param message 当前轮用户消息
     * @return 结合该会话窗口历史生成的回答与 usage
     */
    public AiResponse memoryChat(String conversationId, String message) {
        // MemoryAdvisor 是“记忆如何进入/离开模型调用”的策略；ChatMemory 是实际消息存储。
        MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        ChatResponse response = chatClient.prompt()
                .system("你是一个能结合当前会话历史回答问题的中文助手。")
                .user(message)
                .advisors(advisor -> advisor
                        // MemoryAdvisor 先取历史并注入 Prompt，响应后把本轮 User/Assistant Message 写回存储。
                        .advisors(memoryAdvisor, lifecycleLoggerAdvisor)
                        // 同一个 Advisor 可服务多个会话；conversationId 通过 Advisor 上下文按请求传入。
                        .param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .chatResponse();
        return AiResponse.from(response);
    }

    /**
     * 清空指定会话消息，不影响其他 conversationId。
     *
     * @param conversationId 要清理的会话隔离键
     */
    public void clearMemory(String conversationId) {
        chatMemory.clear(conversationId);
    }
}
