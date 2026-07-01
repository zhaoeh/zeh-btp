package com.ai.service;

import com.ai.advisor.AiLifecycleLoggerAdvisor;
import com.ai.dto.SqlAnalysisResult;
import com.ai.tool.SqlTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * SQL 分析 Agent 编排服务。
 * 组合 System/User Prompt、SQL Tool、生命周期 Advisor 和 Structured Output，
 * 展示一次可能包含工具回合的完整 ChatClient 调用。
 */
@Service
@RequiredArgsConstructor
public class SQLAgentService {


    private final ChatClient chatClient;

    private final SqlTools sqlTools;

    private final AiLifecycleLoggerAdvisor lifecycleLoggerAdvisor;

    /**
     * 让模型以 DBA 角色分析 SQL，并映射为固定 Java 类型。
     *
     * @param sql 待分析 SQL；这里只分析文本，不直接执行原 SQL
     * @return 风险级别、扫描特征和优化建议
     * @throws IllegalArgumentException SQL 为空或超过教学接口长度上限
     */
    public SqlAnalysisResult analyze(String sql) {

        if (sql == null || sql.isBlank() || sql.length() > 10_000) {
            throw new IllegalArgumentException("SQL不能为空且长度不能超过10000个字符");
        }

        /*
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
        // prompt() 创建本次请求的可变构建规格，尚未访问模型。
        return chatClient.prompt()
                // SystemMessage 约束角色与总体任务，优先级高于普通用户消息。
                .system("""
                        你是一个资深MySQL DBA。
                        
                        你会自动调用工具分析SQL。
                        
                        分析之后的结果尽量是中文。
                        
                        而且请务必给出详细的分析步骤。
                        """)
                // UserMessage 放置本次待分析 SQL；这里使用 formatted 生成最终消息文本。
                .user("""
                        请详细分析以下SQL，尤其注意*和索引字段等：
                        
                        %s
                        """.formatted(sql))
                // 把 @Tool 方法定义发送给模型。模型可决定调用 explain，Spring AI 负责执行并回传工具结果。
                .tools(sqlTools)
                .advisors(lifecycleLoggerAdvisor)
                // call() 可能包含“模型请求工具 + 工具结果后的最终模型回答”多个模型回合。
                .call()
                // entity() 使用 BeanOutputConverter 将最终模型文本转换为 SqlAnalysisResult。
                .entity(SqlAnalysisResult.class);
    }

}
