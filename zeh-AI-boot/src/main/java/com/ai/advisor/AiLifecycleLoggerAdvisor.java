package com.ai.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientMessageAggregator;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * 一个最小化的自定义 Advisor：展示请求进入、模型调用、响应返回三个阶段。
 * 日志刻意不输出 Prompt 和回答正文，避免把业务敏感数据写入日志。
 */
@Slf4j
@Component
public class AiLifecycleLoggerAdvisor implements CallAdvisor, StreamAdvisor {

    /**
     * 包装一次同步调用。调用 {@code nextCall} 才会把请求继续交给后续 Advisor/ChatModel，
     * 因而前后两段代码分别处于模型调用前和模型响应后。
     *
     * @param request 已完成 Prompt 构建、正沿 Advisor 链传递的请求
     * @param chain 剩余同步 Advisor 与最终模型调用组成的责任链
     * @return 不改变内容、原样向上游返回的 ChatClientResponse
     */
    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        long startedAt = System.nanoTime();
        // getInstructions() 包含本次 Prompt 的 System/User/Assistant/Tool 等消息，但这里只记录数量。
        log.info("AI lifecycle started: messages={}", request.prompt().getInstructions().size());
        // 必须调用 nextCall 才会推进责任链；遗漏它会导致模型永远不被调用。
        ChatClientResponse response = chain.nextCall(request);
        logCompleted(response.chatResponse(), startedAt, "call");
        return response;
    }

    /**
     * 包装一次流式调用。流式响应由许多增量 ChatClientResponse 组成，
     * 先聚合出完整响应后再记录一次最终 token 与耗时，避免每个片段重复打日志。
     *
     * @param request 流式 Prompt 请求
     * @param chain 剩余流式 Advisor 与模型调用链
     * @return 仍可被下游逐片消费的响应流
     */
    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {
        long startedAt = System.nanoTime();
        log.info("AI lifecycle started: mode=stream, messages={}", request.prompt().getInstructions().size());
        // ChatClientMessageAggregator 观察并聚合流，而不是把最终业务 Flux 改造成单元素响应。
        return new ChatClientMessageAggregator().aggregateChatClientResponse(
                chain.nextStream(request), response -> logCompleted(response.chatResponse(), startedAt, "stream"));
    }

    /**
     * 统一记录同步与流式调用的非敏感完成信息。
     *
     * @param response Spring AI 模型响应；异常或空响应场景下可能为 null
     * @param startedAt 使用 nanoTime 记录的单调时钟起点
     * @param mode call 或 stream，用于区分调用模式
     */
    private void logCompleted(ChatResponse response, long startedAt, String mode) {
        long elapsedMs = (System.nanoTime() - startedAt) / 1_000_000;
        if (response == null) {
            log.warn("AI lifecycle completed without response: mode={}, elapsedMs={}", mode, elapsedMs);
            return;
        }
        log.info("AI lifecycle completed: mode={}, model={}, totalTokens={}, elapsedMs={}",
                mode,
                response.getMetadata().getModel(),
                response.getMetadata().getUsage().getTotalTokens(),
                elapsedMs);
    }

    /**
     * Advisor 在观测和调试信息中的稳定名称。
     *
     * @return 当前 Advisor 的简单类名
     */
    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    /**
     * Advisor 链执行顺序。数值越小越靠外层/越早执行；相同顺序按注册次序处理。
     *
     * @return 本示例的固定顺序 100
     */
    @Override
    public int getOrder() {
        return 100;
    }
}
