package jp.co.futech.module.insight.sqltemplate.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jooq.CommonTableExpression;

import java.io.Serializable;
import java.util.List;


/**
 * @description: with表达式对象
 * @author: ErHu.Zhao
 * @create: 2024-07-29
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EventSqlExpression implements Serializable {

    private List<RootExpression> expressions;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class RootExpression {
        // 当前自身with表达式
        private CommonTableExpression selfExpression;

        // 子节点聚合后的with表达式
        private CommonTableExpression subJoinedExpression;

        // 当前节点和子节点聚合后的with表达式
        private CommonTableExpression joinedExpression;

        private List<SubExpression> subExpressions;

        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Data
        public static class SubExpression {
            private CommonTableExpression selfExpression;
            private boolean isOr = false;
        }
    }
}
