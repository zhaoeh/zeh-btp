package com.ai;

import com.ai.service.KnowledgeService;
import com.ai.tool.OrderTool;
import com.ai.tool.SqlTools;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AiBuildingBlocksTest {

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

    @Test
    void toolShouldRejectInvalidIdsBeforeTouchingBusinessSystems() {
        OrderTool orderTool = new OrderTool();

        assertThatThrownBy(() -> orderTool.getOrder(0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("订单ID");
    }

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
