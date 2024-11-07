package jp.co.futech.module.insight.sqltemplate.core;

import cn.hutool.core.collection.CollectionUtil;
import jp.co.futech.module.insight.sqltemplate.template.EventSqlExpression;
import jp.co.futech.module.insight.sqltemplate.template.EventSqlTemplate;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

/**
 * @description: sql模板转换器
 * @author: ErHu.Zhao
 * @create: 2024-07-30
 **/
@Component
public class EventSqlTemplateConverter {

    @Autowired(required = false)
    private EventSqlTemplateRefresher refresher;

    @Autowired
    private PrimaryTemplateParser primaryTemplateParser;

    @Autowired
    private SubTemplateParser subTemplateParser;

    @Autowired
    private DSLContext dsl;

    public EventSqlExpression convert(EventSqlTemplate template) {
        EventSqlExpression expression = new EventSqlExpression();
        if (Objects.nonNull(refresher)) {
            refresher.refresh(template);
        }
        EventSqlTemplate.SqlMetaData sqlMetaData = template.getSqlMetaData();
        List<EventSqlTemplate.SqlMetaData.CalcSql> calcSqls = sqlMetaData.getCalcSql();
        List<EventSqlTemplate.Dimension> dimension = sqlMetaData.getDimension();
        EventSqlTemplate.FilterData filterData = sqlMetaData.getFilterData();
        expression.setExpressions(parseCalcSqls(calcSqls, dimension, filterData));
        return expression;
    }


    /**
     * 解析calcSql维度
     *
     * @param calcSqls
     * @param dimension
     * @param filterData
     */
    public List<EventSqlExpression.RootExpression> parseCalcSqls(List<EventSqlTemplate.SqlMetaData.CalcSql> calcSqls,
                                                                 List<EventSqlTemplate.Dimension> dimension,
                                                                 EventSqlTemplate.FilterData filterData) {
        List<EventSqlExpression.RootExpression> expressions = new ArrayList<>();
        final AtomicInteger count = new AtomicInteger(1);
        calcSqls.stream().forEach(v -> {
            List<EventSqlTemplate.SqlMetaData.CalcSql.FiltersCol> filtersCols = v.getFiltersCol();
            expressions.add(parseFiltersCols(count, v, filtersCols, dimension, filterData));
            count.getAndIncrement();
        });
        return expressions;
    }


    public EventSqlExpression.RootExpression parseFiltersCols(
            AtomicInteger count,
            EventSqlTemplate.SqlMetaData.CalcSql calcSql,
            List<EventSqlTemplate.SqlMetaData.CalcSql.FiltersCol> filtersCols,
            List<EventSqlTemplate.Dimension> dimension,
            EventSqlTemplate.FilterData filterData) {
        List<EventSqlTemplate.SqlMetaData.CalcSql.FiltersCol> primaryConditions = null;
        List<EventSqlTemplate.SqlMetaData.CalcSql.FiltersCol> subDescriptions = null;

        if (CollectionUtil.isNotEmpty(filtersCols)) {
            Map<Boolean, List<EventSqlTemplate.SqlMetaData.CalcSql.FiltersCol>> allSubs =
                    filtersCols.stream().collect(Collectors.partitioningBy(s -> Objects.equals(s.getFilterColTableName(), calcSql.getColTableName())));
            primaryConditions = allSubs.get(true);
            subDescriptions = allSubs.get(false);
        }

        EventSqlExpression.RootExpression rootExpression = primaryTemplateParser.processPrimaryNode(calcSql, primaryConditions, dimension, filterData, count);
        subTemplateParser.processSubNodes(rootExpression, subDescriptions, count);
        subTemplateParser.polySubNodes(rootExpression);
        polyPrimaryAndPolyedSubNodes(rootExpression);
        return rootExpression;
    }

    /**
     * 聚合主节点和聚合后的子节点为一个临时表
     *
     * @param rootExpression
     */
    private void polyPrimaryAndPolyedSubNodes(EventSqlExpression.RootExpression rootExpression) {
        // 主子聚合
        CommonTableExpression initExp = rootExpression.getSelfExpression();
        SelectJoinStep initSelect = dsl.select(initExp.fields()).from(initExp);
        CommonTableExpression previousWith = initExp;
        SelectJoinStep iteratorSelect = initSelect;
        ResultQuery finalSelect;
        if (Objects.nonNull(rootExpression)) {
            CommonTableExpression swith = rootExpression.getSubJoinedExpression();
            if (Objects.nonNull(swith)) {
                Field<Object> left = field(name(previousWith.getName(), previousWith.field(0).getName()));
                Field<Object> right = field(name(swith.getName(), swith.field(0).getName()));
                iteratorSelect = iteratorSelect.innerJoin(swith).on(left.eq(right));
                finalSelect = iteratorSelect;
            } else {
                finalSelect = initExp.$query();
                rootExpression.setSelfExpression(null);
            }
            if (Objects.nonNull(finalSelect)) {
                String primaryWithAliasName = initExp.$name().as().$name().last();
                Name currentTableWithName = name(primaryWithAliasName + "_join");
                CommonTableExpression primaryJoinSubWithExp = currentTableWithName.as(finalSelect);
                rootExpression.setJoinedExpression(primaryJoinSubWithExp);
            }
        }
    }
}
