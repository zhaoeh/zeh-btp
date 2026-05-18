package com.ai.service;

import com.ai.dto.SqlAnalysisResult;
import com.ai.tool.SqlTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SQLAgentService {


    private final ChatClient chatClient;

    private final SqlTools sqlTools;

    /**
     * 分析SQL
     *
     * @param sql
     * @return
     */
    public SqlAnalysisResult analyze(String sql) {

        /**
         * chatClient.prompt()
         *         .system(...)
         *         .user(...)
         *         .tools(...)
         *         .call()
         *         .entity(...)
         * 一次完整的AI推理生命周期，说白了就是这一堆链式api调用编排组装起来的
         * system()：定义System Prompt，即系统级别的prompt，系统级别的提示词，可以理解为AI的操作系统指令，赋予AI一个系统级别的指令规则
         * user()：定义用户级别的 Prompt，即用户在问什么
         * 简单点理解：system()指定AI是谁；user()指定用户在问什么具体问题。二者都用来指定prompt
         * system()级别的prompt极其重要，因为AI本质并没有稳定人格，system()本质是在宏观上约束模型，告诉AI自己到底是个什么底色。
         * 在真实的企业中，system()往往非常长：禁止胡说，禁止骂人，禁止说脏话，禁止暴露敏感信息
         * system()表示的prompt是优先级最高的，它表示这个AI的底核是什么。
         * 其次才是user()指定的用户级别的输入prompt
         *
         *
         * assistant：表示历史回答，其本质也是一个prompt
         * tool()：工具，可以理解为为AI设置的应用层插件，以便AI自己完成推理后，可以拿来使用的工具
         * call():真正发起推理，即真正开始向LLM发起请求，前面的一系列都是在构建推理请求，可以理解为在构建推理上下文
         */
        return chatClient.prompt()
                .system("""
                        你是一个资深MySQL DBA。
                        
                        你会自动调用工具分析SQL。
                        
                        分析之后的结果尽量是中文。
                        
                        而且请务必给出详细的分析步骤。
                        """)
                .user("""
                        请详细分析以下SQL，尤其注意*和索引字段等：
                        
                        %s
                        """.formatted(sql))
                .tools(sqlTools)
                .call()
                .entity(SqlAnalysisResult.class);
    }

}
