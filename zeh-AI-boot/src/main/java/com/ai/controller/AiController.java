package com.ai.controller;

import com.ai.advisor.AiLifecycleLoggerAdvisor;
import com.ai.tool.OrderTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Spring AI 最小能力演示接口。
 *
 * <p>保留最短调用链，分别展示同步对话、SSE 流式对话和 Tool Calling。
 * 更完整的 Prompt、Memory、Structured Output 示例位于 {@link AiLearningController}。</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AiController {

    private final ChatClient chatClient;

    private final OrderTool orderTool;

    private final AiLifecycleLoggerAdvisor lifecycleLoggerAdvisor;


    /**
     * 最小同步对话：请求线程阻塞等待模型生成完整答案。
     *
     * @param msg 用户原始问题，Spring MVC 从 text/plain 请求体读取
     * @return 模型最终回答正文
     */
    @PostMapping("/demo")
    public String demo(@RequestBody String msg) {


        // prompt(msg)：把字符串作为 UserMessage，创建本次调用的 ChatClientRequestSpec。
        return chatClient.prompt(msg)
                // advisors(...)：把横切逻辑加入本次请求；此处只记录安全的生命周期元数据。
                .advisors(lifecycleLoggerAdvisor)
                // call()：终止请求构建并同步调用 ChatModel；在此之前都没有访问大模型。
                .call()
                // content()：从 ChatResponse 的第一条 Generation 中提取纯文本。
                .content();
    }

    /**
     * Flux流式响应到客户端，逐帧响应
     *
     * @param msg http msg
     * @return 流式响应
     */
    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestBody String msg) {
        // prompt 和 Advisor 的构建过程与同步调用一致。
        return chatClient.prompt(msg)
                .advisors(lifecycleLoggerAdvisor)
                // stream()：发起响应式模型调用，答案片段到达后逐个向下游发布。
                .stream()
                // 流式 content() 返回 Flux<String>；Spring MVC 按 SSE 帧持续写给客户端。
                .content();
    }


    /**
     * 入门版的AI Agent
     *
     * @param msg 请求消息，内部会转化为prompt
     * @return AI响应
     */
    @PostMapping("/agent")
    public String agent(@RequestBody String msg) {
        // tools(orderTool)：扫描对象中标注 @Tool 的方法，并把名称、说明、参数 Schema 发送给模型。
        return chatClient.prompt(msg)
                .tools(orderTool)
                .advisors(lifecycleLoggerAdvisor)
                // call() 内部可能发生多轮：模型提出工具调用 -> Java 执行工具 -> 工具结果回传模型 -> 最终回答。
                .call()
                .content();
    }
}
