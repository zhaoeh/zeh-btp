# zeh-AI-boot：Spring AI 学习与落地手册

本模块以 Spring Boot 3.5.7、Spring AI 1.0.0、Ollama 为运行基线。本文从一次请求的完整生命周期出发，依次练习 Prompt、结构化输出、Memory、RAG、Tool Calling 和 Advisor。

## 1. 扫描结论

### 已在本次改造中解决

| 原有问题 | 风险 | 当前实现 |
| --- | --- | --- |
| Controller 混用字段注入和 Lombok 构造器注入 | 难测试、依赖不透明 | 统一构造器注入 |
| 只返回字符串 | 无法看到模型、token 等元数据 | `AiResponse` 返回内容与 usage |
| Prompt 依赖字符串拼接 | 复用差，变量边界不清晰 | Prompt Template + `.param()` |
| 没有对话记忆 | 模型每次调用都无状态 | `MessageWindowChatMemory` + Memory Advisor |
| RAG 在 `@PostConstruct` 强制连接 Ollama | Ollama 故障会拖垮应用启动 | 启动默认跳过，支持手动 reload，自动模式失败也会降级 |
| RAG 只做裸 `similaritySearch(question)` | 无阈值、Top-K、元数据、引用 | 增加检索参数、元数据、来源和摘要 |
| RAG 无“无相关文档”分支 | 容易把不相关上下文交给模型 | 空结果直接返回，不调用 LLM |
| RAG Prompt 无防注入边界 | 文档中的恶意命令可能被执行 | 使用 knowledge 边界并声明资料不是指令 |
| Tool 参数缺少语义和校验 | 模型容易传错参数，危险 SQL 可能进入工具 | `@ToolParam` + ID/只读 SQL 校验 |
| 没有输入校验和统一 400 响应 | 超长、空输入直接进入模型 | Bean Validation + Problem Detail |
| 看不到调用生命周期 | 难定位耗时和 token 消耗 | 自定义 `AiLifecycleLoggerAdvisor`，且不记录正文 |
| 没有测试 | 关键边界易回归 | 增加知识元数据和 Tool 安全边界测试 |

### 仍建议后续处理（本次未擅自扩大修改范围）

1. 根 `pom.xml` 的 `<modules>` 为空，AI 模块不参与根工程聚合构建。
2. AI 模块的父版本是 Spring Boot 3.5.7，但属性中还保留未生效的 `spring.boot.version=3.3.10`，并存在较多与 AI 无关的历史版本属性和依赖。
3. Spring AI 1.0.0 已不是 1.0.x 最新补丁版本；升级到 1.0.9 或 2.0 需要单独做兼容性验证，不宜混进案例改造。
4. `SimpleVectorStore` 和内存 ChatMemory 都会在重启后丢失；生产应替换为 PGVector/Milvus/Redis 等持久化实现。
5. 当前 SQL `EXPLAIN` 是教学桩，不连接真实数据库。生产实现还要增加只读数据源、超时、行数限制和审计。
6. 尚未接入鉴权、租户隔离、限流、配额、内容安全、敏感信息脱敏、重试/熔断和模型降级。
7. 尚未接入 Actuator/Micrometer 导出端；Spring AI 自带 observation，但生产仍需指标和 trace 后端。
8. Structured Output 是“尽力转换”；当前案例已做基础字段范围校验，生产仍应增加失败重试和人工兜底。

这些事项会涉及父工程、公共基础设施、数据库或版本迁移，因此需要另行确认后再实施。

## 2. 一次 Spring AI 请求的完整生命周期

```text
HTTP 请求
  -> Bean Validation（输入边界）
  -> Controller
  -> Service 组装 system/user/messages/options/tools
  -> ChatClient 创建 Prompt
  -> Advisor before（Memory、RAG、日志等）
  -> ChatModel（这里是 OllamaChatModel）
  -> 模型可能要求 Tool Calling
  -> Spring AI 执行 Tool，并把结果作为 ToolMessage 再交给模型
  -> 模型返回 ChatResponse
  -> StructuredOutputConverter（需要 Java 对象时）
  -> Advisor after（写回 Memory、记录元数据）
  -> HTTP 普通响应或 SSE 流式响应
```

三个容易混淆的生命周期要点：

- `ChatClient` 可以复用，但一次 `prompt()...call()` 表示一次新的调用编排。
- 模型 API 天生无状态；Memory Advisor 只是每次调用前把历史消息重新带上，调用后再保存新消息。
- Tool Calling 通常不是一次模型请求：模型先返回工具调用意图，应用执行工具，再把结果交回模型生成最终答案。

## 3. 启动准备

```bash
ollama pull qwen3:4b
ollama pull nomic-embed-text
ollama serve
```

从 IDE 运行 `com.ai.AiApplication`。配置位于 `src/main/resources/application.yml`。

默认不会在启动时导入知识，目的是让 Ollama 暂时不可用时应用仍可启动。需要 RAG 时执行：

```bash
curl -X POST http://localhost:8080/rag/knowledge/reload
```

## 4. 按顺序完成七个案例

### 案例一：最小 ChatClient 调用

```bash
curl -X POST http://localhost:8080/ai/learn/chat \
  -H 'Content-Type: application/json' \
  -d '{"message":"用三句话解释 Spring AI"}'
```

观察 `AiResponse`：`content` 是回答，`model` 和三个 token 字段来自 `ChatResponse.metadata`。

### 案例二：Prompt Template

```bash
curl -X POST http://localhost:8080/ai/learn/prompt-template \
  -H 'Content-Type: application/json' \
  -d '{"topic":"Advisor","audience":"Java初学者","language":"中文"}'
```

重点查看 `AiDemoService.promptTemplate`：system 定义角色，user 定义任务，`.param()` 在运行时填充变量。

### 案例三：Structured Output

```bash
curl -X POST http://localhost:8080/ai/learn/structured-output \
  -H 'Content-Type: application/json' \
  -d '{"message":"新版本启动很快，但导出报表偶尔失败。"}'
```

`.entity(SentimentResult.class)` 会加入格式指令、接收模型文本，再反序列化为 Java record。它不是绝对可靠的 JSON Schema 保证，生产代码仍需校验。

### 案例四：多轮 Memory

```bash
curl -X POST http://localhost:8080/ai/learn/memory/user-1001 \
  -H 'Content-Type: application/json' \
  -d '{"message":"记住，我负责支付系统。"}'

curl -X POST http://localhost:8080/ai/learn/memory/user-1001 \
  -H 'Content-Type: application/json' \
  -d '{"message":"我负责什么系统？"}'

curl -X DELETE http://localhost:8080/ai/learn/memory/user-1001
```

换一个 conversationId 就是另一段隔离的会话。当前隔离仅用于演示，真实系统必须从已认证用户上下文生成 ID，不能完全相信客户端。

### 案例五：完整 RAG

先导入，再问答：

```bash
curl -X POST http://localhost:8080/rag/knowledge/reload

curl 'http://localhost:8080/rag/ask?q=提现多久能到账'
```

代码路径：

1. `KnowledgeService` / `PdfKnowledgeService`：Reader，把知识变成 `Document`。
2. `TokenTextSplitter`：Transformer，按 token 切片。
3. `KnowledgeIngestionService`：调用 EmbeddingModel 并写入 VectorStore。
4. `RagService`：按 `top-k` 和 `similarity-threshold` 检索。
5. 把文档作为受限 knowledge 上下文交给 ChatClient。
6. 返回回答和 `sources`，便于前端展示引用及排查召回质量。

### 案例六：Tool Calling / Agent

```bash
curl -X POST http://localhost:8080/ai/agent \
  -H 'Content-Type: text/plain' \
  --data '查询订单1001，并告诉我用户2001的余额'
```

模型负责选择 `OrderTool` 的方法；Java 方法才是实际能力边界。真实工具必须校验身份、权限、参数、超时和幂等，不能因为调用者是模型就跳过安全检查。

SQL Agent 示例：

```bash
curl -X POST http://localhost:8080/sql/analyze \
  -H 'Content-Type: text/plain' \
  --data 'select * from user where name = "张三" limit 100000,20'
```

### 案例七：流式输出与 Advisor

```bash
curl -N -X POST http://localhost:8080/ai/stream \
  -H 'Content-Type: text/plain' \
  --data '分步骤解释 RAG'
```

`stream()` 返回 `Flux<String>`，通过 SSE 逐段发给客户端。`AiLifecycleLoggerAdvisor` 同时展示了同步和流式 Advisor 的写法；它只记录消息数、模型、token 和耗时，不记录用户正文。

## 5. 推荐的生产演进顺序

1. 先完成鉴权、conversationId 服务端生成、输入长度和模型配额限制。
2. 把 Memory 和 VectorStore 持久化，并按 tenant/user/knowledge-base 做元数据过滤。
3. 为 RAG 建立离线评测集，测召回率、答案忠实度和无答案拒答率，而不是只凭肉眼试问。
4. 工具调用接入权限、审计、超时、幂等和人工确认；写操作与只读操作分级。
5. 接入 Micrometer tracing/metrics，关注 token、延迟、错误率、工具成功率、RAG 空召回率。
6. 增加模型降级与熔断，再考虑多模型路由和更复杂的 Agent planning。

## 6. 官方资料

- ChatClient 与 Advisor：https://docs.spring.io/spring-ai/reference/1.0/api/chatclient.html
- Chat Memory：https://docs.spring.io/spring-ai/reference/1.0/api/chat-memory.html
- Tool Calling：https://docs.spring.io/spring-ai/reference/1.0/api/tools.html
- ETL Pipeline：https://docs.spring.io/spring-ai/reference/1.0/api/etl-pipeline.html
- RAG：https://docs.spring.io/spring-ai/reference/1.0/api/retrieval-augmented-generation.html
- Structured Output：https://docs.spring.io/spring-ai/reference/1.0/api/structured-output-converter.html
- Observability：https://docs.spring.io/spring-ai/reference/observability/index.html
