package com.ai;

import com.ai.service.KnowledgeService;
import com.ai.tool.OrderTool;
import com.ai.tool.SqlTools;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 不依赖 Ollama 的 AI 基础组件单元测试。
 * 重点验证进入外部模型/业务系统之前可以确定的元数据和工具安全边界。
 */
class AiBuildingBlocksTest {

    /** 验证 RAG 文档具备后续过滤、引用和追溯所需的元数据。 */
    @Test
    void knowledgeDocumentsShouldCarryMetadataForFilteringAndCitation() {
        List<Document> documents = new KnowledgeService().loadBuiltInKnowledge();

        assertThat(documents).hasSize(2);
        assertThat(documents)
                .allSatisfy(document -> {
                    assertThat(document.getId()).isNotBlank();
                    assertThat(document.getMetadata()).containsKeys("source", "category");
                });
    }

    /** 验证模型生成的非法工具参数会在业务工具入口被拒绝。 */
    @Test
    void toolShouldRejectInvalidIdsBeforeTouchingBusinessSystems() {
        OrderTool orderTool = new OrderTool();

        assertThatThrownBy(() -> orderTool.getOrder(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("订单ID");
    }

    /** 验证 SQL 工具接受只读查询，并拒绝写操作和多语句注入。 */
    @Test
    void sqlToolShouldOnlyAcceptReadOnlySingleStatement() {
        SqlTools sqlTools = new SqlTools();

        assertThat(sqlTools.explain("select id from user where id = 1")).contains("type:ALL");
        assertThatThrownBy(() -> sqlTools.explain("delete from user"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("SELECT");
        assertThatThrownBy(() -> sqlTools.explain("select * from user; drop table user"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
