package jp.co.futech.module.insight.sqltemplate.core;

import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.PostConstruct;
import jp.co.futech.module.insight.sqltemplate.template.EventSqlTemplate;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;

/**
 * @description: 子句处理器（where/order by/group by等子句处理）
 * @author: ErHu.Zhao
 * @create: 2024-07-30
 **/
@Component
public class ClauseProcessor {
    private Map<String, BiFunction<Field<Object>, Object, Condition>> conditionRules1 = new HashMap<>();
    private Map<String, Function<Field<Object>, Condition>> conditionRules2 = new HashMap<>();
    private Map<String, Function<Field<Object>, Field<Object>>> fieldTypes = new HashMap<>();

    @Autowired
    private FunctionProcessor functionProcessor;

    @PostConstruct
    public void prepareConditionRules() {
        conditionRules1.put("1", Field::greaterThan);
        conditionRules1.put("2", Field::lessThan);
        conditionRules1.put("3", Field::equal);
        conditionRules1.put("4", Field::notEqual);
        conditionRules1.put("5", Field::greaterOrEqual);
        conditionRules1.put("6", Field::lessOrEqual);
        conditionRules2.put("7", Field::isNull);
        conditionRules2.put("8", Field::isNotNull);
        fieldTypes.put("1", functionProcessor::original);
        fieldTypes.put("2", functionProcessor::original);
        fieldTypes.put("3", functionProcessor::toDate);
        fieldTypes.put("4", functionProcessor::original);
    }

    /**
     * 拼接where条件
     *
     * @param primaryQuery
     * @param selectJoinStep
     * @param primaryConditions
     * @return
     */
    public Select processWhere(Select primaryQuery,
                               SelectJoinStep selectJoinStep,
                               List<EventSqlTemplate.SqlMetaData.CalcSql.FiltersCol> primaryConditions,
                               EventSqlTemplate.FilterData appendPrimaryConditions) {
        SelectConditionStep iteratorSelect = null;
        // 子条件存在则拼装
        if (CollUtil.isNotEmpty(primaryConditions)) {
            EventSqlTemplate.SqlMetaData.CalcSql.FiltersCol initCondition = primaryConditions.get(0);
            Condition initC = condition(initCondition.getFilterSymbol(), wrapField(initCondition.getFilterColType(), initCondition.getFilterColName()), initCondition.getFilterContent());
            SelectConditionStep initConditionStep = selectJoinStep.where(initC);
            iteratorSelect = initConditionStep;
            for (int i = 1; i < primaryConditions.size(); i++) {
                EventSqlTemplate.SqlMetaData.CalcSql.FiltersCol currentCondition = primaryConditions.get(i);
                Condition iteratorC = condition(currentCondition.getFilterSymbol(), wrapField(currentCondition.getFilterColType(), currentCondition.getFilterColName()), currentCondition.getFilterContent());
                iteratorSelect = iteratorSelect.and(iteratorC);
            }
            primaryQuery = iteratorSelect;
        }
        // 附加条件存在则拼装
        if (Objects.nonNull(appendPrimaryConditions)) {
            List<EventSqlTemplate.FilterData.Filter> filters = appendPrimaryConditions.getFilters();
            if (CollUtil.isNotEmpty(filters)) {
                // 拼接初始化条件
                EventSqlTemplate.FilterData.Filter initCondition = filters.get(0);
                Condition initC = condition(initCondition.getFilterSymbol(), wrapField(initCondition.getFilterColType(), initCondition.getFilterColName()), initCondition.getFilterContent());
                SelectConditionStep initConditionStep = Objects.isNull(iteratorSelect) ?
                        selectJoinStep.where(initC) :
                        iteratorSelect.and(initC);
                iteratorSelect = initConditionStep;
                for (int i = 1; i < filters.size(); i++) {
                    EventSqlTemplate.FilterData.Filter currentCondition = filters.get(i);
                    Condition iteratorC = condition(currentCondition.getFilterSymbol(), wrapField(currentCondition.getFilterColType(), currentCondition.getFilterColName()), currentCondition.getFilterContent());
                    iteratorSelect = iteratorSelect.and(iteratorC);
                }
                primaryQuery = iteratorSelect;
            }
        }
        return primaryQuery;
    }

    /**
     * 处理group by
     *
     * @param primaryQuery
     * @param groupFields
     */
    public void processGroupBy(SelectJoinStep primaryQuery, List<Field<?>> groupFields) {
        List<Field<?>> defaultGroupBy = primaryQuery.getSelect();
        if (CollUtil.isNotEmpty(defaultGroupBy)) {
            primaryQuery.groupBy(defaultGroupBy.stream().limit(2).collect(Collectors.toList()));
        }
        // 存在grouping则拼装
        if (CollUtil.isNotEmpty(groupFields)) {
            primaryQuery.groupBy(groupFields);
        }
    }

    /**
     * 获取condition条件表达式
     *
     * @param strategy
     * @param field
     * @param value
     * @return
     */
    private Condition condition(String strategy, Field<Object> field, Object value) {
        Function<Field<Object>, Condition> conditionFunction = conditionRules2.get(strategy);
        if (Objects.nonNull(conditionFunction)) {
            return conditionFunction.apply(field);
        }
        BiFunction<Field<Object>, Object, Condition> conditionBiFunction = conditionRules1.get(strategy);
        if (Objects.nonNull(conditionBiFunction)) {
            return conditionBiFunction.apply(field, value);
        }
        return null;
    }

    /**
     * 包装field，根据字段类型动态添加函数
     *
     * @param strategy
     * @param name
     * @return
     */
    private Field<Object> wrapField(String strategy, String name) {
        Function<Field<Object>, Field<Object>> fieldFieldFunction = fieldTypes.get(strategy);
        if (Objects.nonNull(fieldFieldFunction)) {
            return fieldFieldFunction.apply(field(name));
        }
        return null;
    }

}
