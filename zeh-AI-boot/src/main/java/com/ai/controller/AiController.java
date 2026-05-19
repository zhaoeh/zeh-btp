package com.ai.controller;

import com.ai.tool.OrderTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AiController {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private OrderTool orderTool;


    @PostMapping("/demo")
    public String demo(@RequestBody String msg) {


        // prompt：提示词，其实就是客户端用户的提问
        // call()方法：表示使用客户端录入的提示词进行LLM大语言模型调用
        // content()方法：表示输出LLM大语言模型的推理结果
        return chatClient.prompt(msg)
                .call()
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
        return chatClient.prompt(msg)
                .stream()
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
        // tools()方法，即允许传入应用册定义的多个tool对象，模型会自动推理，按照推理逻辑去调用相关的tool
        return chatClient.prompt(msg)
                .tools(orderTool)
                .call()
                .content();
    }
}