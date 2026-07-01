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

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest request, CallAdvisorChain chain) {
        long startedAt = System.nanoTime();
        log.info("AI lifecycle started: messages={}", request.prompt().getInstructions().size());
        ChatClientResponse response = chain.nextCall(request);
        logCompleted(response.chatResponse(), startedAt, "call");
        return response;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {
        long startedAt = System.nanoTime();
        log.info("AI lifecycle started: mode=stream, messages={}", request.prompt().getInstructions().size());
        return new ChatClientMessageAggregator().aggregateChatClientResponse(
                chain.nextStream(request), response -> logCompleted(response.chatResponse(), startedAt, "stream"));
    }

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

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
