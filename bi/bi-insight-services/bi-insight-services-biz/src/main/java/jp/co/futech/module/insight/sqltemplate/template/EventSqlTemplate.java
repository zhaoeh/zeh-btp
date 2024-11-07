package jp.co.futech.module.insight.sqltemplate.template;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jooq.CommonTableExpression;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 事件SQL模板
 * @author: ErHu.Zhao
 * @create: 2024-07-24
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EventSqlTemplate implements Serializable {
    private String taskName;
    private String indAliasName;
    @JsonProperty("sqlMetaData")
    private SqlMetaData sqlMetaData;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class SqlMetaData {
        private List<CalcSql> calcSql;
        private List<Dimension> dimension;
        private FilterData filterData;

        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Data
        public static class CalcSql {
            private String colName;
            private String colRename;
            private String colTableName;
            private String colType;
            private String colCalculation;
            private String minuDts;
            private String userIdField;
            private String dtField;
            private List<FiltersCol> filtersCol;

            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            @Data
            public static class FiltersCol {
                private String filterColName;
                private String filterColRename;
                private String filterColType;
                private String filterColTableName;
                private String filterSymbol;
                private String filterContent;
                private String beforeRelation;
                private String userIdField;
                private String dtField;
                private boolean isOr = false;

            }
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Dimension {
        private String colName;
        private String colRename;
        private String colTableName;
        private String dataType;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class FilterData {

        private List<Filter> filters;

        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Data
        public static class Filter {
            private String filterColName;
            private String filterColRename;
            private String filterColType;
            private String filterColTableName;
            private String filterSymbol;
            private String filterContent;
            private String beforeRelation;
        }
    }
}
