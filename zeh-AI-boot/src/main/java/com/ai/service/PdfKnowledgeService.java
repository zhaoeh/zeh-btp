package com.ai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * classpath 知识文件的 Reader + Transformer 示例。
 * 类名沿用原 Demo；当前读取的是 UTF-8 文本而非二进制 PDF。
 */
@Service
public class PdfKnowledgeService {

    /**
     * 从 classpath 读取提现规则，封装为 Document 后按 token 切片。
     *
     * @return 保留来源元数据的文档切片
     * @throws Exception classpath 资源不存在或读取失败
     */
    public List<Document> loadResource() throws Exception {

        // ClassPathResource 能同时适配 IDE 目录和打包后的 jar，不依赖操作系统绝对路径。
        Resource resource =
                new ClassPathResource("docs/提现规则.txt");

        // 明确 UTF-8，避免不同运行环境使用系统默认字符集导致中文乱码。
        String text = StreamUtils.copyToString(
                resource.getInputStream(),
                StandardCharsets.UTF_8
        );

//        Path path = Paths.get("docs/提现规则.txt");
//
//        String text = Files.readString(path);

        // Document 同时携带正文、稳定来源 ID 和可用于检索过滤/结果引用的 metadata。
        List<Document> documents = List.of(new Document(
                "withdraw-rule-resource",
                text,
                Map.of("source", "docs/提现规则.txt", "category", "rule")));


        // TokenTextSplitter(int chunkSize, int minChunkSizeChars, int minChunkLengthToEmbed, int maxNumChunks, boolean keepSeparator)
        // 这个类是文本切片类，目的就是按照指定的切片规则将一堆Documents进行合理切片
        // chunkSize：切片大小，切片大小的本质是占用的token数，所以此处的chunkSize本质上是指定每个切片消耗的目标token数，比如一个切片占用200个token，那这个数值就设置200即可
        // minChunkSizeChars:chunk最小字符数,这是字符数，不是token。之所以需要它，是因为完全按照token切出来，可能切出来极小的chunk，比如你好，这独立占用一个trunk，完全没意义，太短，理论上就应该丢弃，因为参与语义化价值不大
        // 极小的trunk：
        //（1）embedding 没意义
        //（2）检索质量极差
        // 比如设置minChunkSizeChars = 400，表示小于400长度的trunk，尽量合并
        // minChunkLengthToEmbed：最小允许参与 embedding 的 chunk 长度，比如设置5，表示长度小于5的chunk直接丢弃
        //
        // 无参构造使用 Spring AI 推荐默认切片参数；实际项目应通过评测调整 chunk 大小和重叠策略。
        TokenTextSplitter splitter = new TokenTextSplitter();

        // apply() 只做 Document -> Document 切片转换，此处尚未调用 EmbeddingModel。
        return splitter.apply(documents);
    }
}
