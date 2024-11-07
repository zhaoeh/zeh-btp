package jp.co.futech.module.insight.sqltemplate.core;

import cn.hutool.core.collection.CollectionUtil;
import jp.co.futech.module.insight.constant.SqlTemplateConstant;
import jp.co.futech.module.insight.sqltemplate.template.EventSqlExpression;
import jp.co.futech.module.insight.sqltemplate.template.EventSqlTemplate;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.jooq.impl.DSL.*;

/**
 * @description: 子节点解析器
 * @author: ErHu.Zhao
 * @create: 2024-07-29
 **/
@Component
public class SubTemplateParser {

    @Autowired
    private DSLContext dsl;

    @Autowired
    private FunctionProcessor functionProcessor;

    /**
     * 处理子节点
     *
     * @param subDescriptions
     * @param rootExpression
     * @param count
     */
    public void processSubNodes(EventSqlExpression.RootExpression rootExpression,
                                List<EventSqlTemplate.SqlMetaData.CalcSql.FiltersCol> subDescriptions,
                                AtomicInteger count) {
        List<EventSqlExpression.RootExpression.SubExpression> subExpressions = new ArrayList<>();
        // 设置子with
        if (CollectionUtil.isNotEmpty(subDescriptions)) {
            final AtomicInteger subCount = new AtomicInteger(1);
            subDescriptions.stream().forEach(sub -> {
                String subTableName = sub.getFilterColTableName();
                sub.setOr(Objects.equals(sub.getBeforeRelation(), SqlTemplateConstant.OR));
                Select subQuery = dsl.select(field(sub.getUserIdField()).as(SqlTemplateConstant.LOGIN_NAME),
                                functionProcessor.toDate(field(sub.getDtField())).as(SqlTemplateConstant.DT)).
                        from(table(subTableName)).
                        where(field(sub.getFilterColName()).eq(sub.getFilterContent()));
                Name subWithName = name(subTableName + "_" + count.get() + subCount.get());
                // 构建子表with表达式
                CommonTableExpression subWithExp = subWithName.as(subQuery);
                subCount.getAndIncrement();
                EventSqlExpression.RootExpression.SubExpression subExpression = new EventSqlExpression.RootExpression.SubExpression();
                subExpression.setSelfExpression(subWithExp);
                subExpressions.add(subExpression);
            });
            rootExpression.setSubExpressions(subExpressions);
        }
    }

    /**
     * 聚合子Node为一个临时表
     *
     * @param rootExpression
     */
    public void polySubNodes(EventSqlExpression.RootExpression rootExpression) {
        // 聚合子with为一个with
        Optional.ofNullable(rootExpression).map(EventSqlExpression.RootExpression::getSubExpressions).ifPresent(subExpressions -> {
            if (CollectionUtil.isNotEmpty(subExpressions)) {
                if (subExpressions.size() == 1) {
                    rootExpression.setSubJoinedExpression(subExpressions.get(0).getSelfExpression());
                } else {
                    CommonTableExpression initExp = subExpressions.get(0).getSelfExpression();
                    SelectJoinStep initSelect = dsl.select(initExp.fields()).from(initExp);
                    CommonTableExpression previousWith = initExp;
                    SelectJoinStep iteratorSelect = initSelect;
                    for (int i = 1; i < subExpressions.size(); i++) {
                        // 当前子表的with表达式
                        CommonTableExpression swith = subExpressions.get(i).getSelfExpression();
                        Field<Object> left = field(name(previousWith.getName(), previousWith.field(0).getName()));
                        Field<Object> right = field(name(swith.getName(), swith.field(0).getName()));
                        iteratorSelect = iteratorSelect.innerJoin(swith).on(left.eq(right));
                    }
                    if (Objects.nonNull(iteratorSelect)) {
                        String primaryWithAliasName = rootExpression.getSelfExpression().$name().as().$name().last();
                        Select finalSelect = iteratorSelect;
                        // 当前节点聚合后的with,将聚合query构建一个全新的聚合with表达式
                        Name currentTableWithName = name(primaryWithAliasName + "_sub_join");
                        CommonTableExpression subJoinSubWithExp = currentTableWithName.as(finalSelect);
                        rootExpression.setSubJoinedExpression(subJoinSubWithExp);
                    }
                }
            }
        });
    }
}
