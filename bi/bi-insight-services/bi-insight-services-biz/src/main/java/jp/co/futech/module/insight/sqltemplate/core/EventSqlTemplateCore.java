package jp.co.futech.module.insight.sqltemplate.core;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import jp.co.futech.module.insight.sqltemplate.template.EventSqlExpression;
import jp.co.futech.module.insight.sqltemplate.template.EventSqlTemplate;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

/**
 * @description: 事件SQL模板解析器
 * @author: ErHu.Zhao
 * @create: 2024-07-24
 **/
@Component
public class EventSqlTemplateCore {

    @Autowired
    private DSLContext dsl;

    @Autowired
    private EventSqlTemplateConverter converter;

    /**
     * 解析事件SQL模板对象
     *
     * @param template
     */
    public void parse(EventSqlTemplate template) {
        processTemplate(converter.convert(template));
    }


    /**
     * 最终聚合所有临时表
     *
     * @param expression
     */
    private void processTemplate(EventSqlExpression expression) {
        final List<CommonTableExpression> totalWiths = new ArrayList<>();
        expression.getExpressions().stream().forEach(l -> {
            List<EventSqlExpression.RootExpression.SubExpression> subExpressions = l.getSubExpressions();
            if (CollUtil.isNotEmpty(subExpressions) && subExpressions.size() > 1) {
                subExpressions.stream().forEach(s -> totalWiths.add(s.getSelfExpression()));
            }
            totalWiths.add(l.getSubJoinedExpression());
            totalWiths.add(l.getSelfExpression());
            totalWiths.add(l.getJoinedExpression());
        });

        Select query;
        List<CommonTableExpression> allJoinedWiths = null;
        List<EventSqlExpression.RootExpression> rootExpressions = expression.getExpressions();
        if (CollectionUtil.isNotEmpty(rootExpressions)) {
            allJoinedWiths = rootExpressions.stream().map(EventSqlExpression.RootExpression::getJoinedExpression).
                    filter(Objects::nonNull).collect(Collectors.toList());
        }
        if (allJoinedWiths !=null && CollectionUtil.isNotEmpty(allJoinedWiths)) {
            CommonTableExpression initExp = allJoinedWiths.get(0);
            SelectJoinStep initSelect = dsl.select(initExp.fields()).from(initExp);
            CommonTableExpression previousWith = initExp;
            SelectJoinStep iteratorSelect = initSelect;
            for (int i = 1; i < allJoinedWiths.size(); i++) {
                CommonTableExpression nextWith = allJoinedWiths.get(i);
                Field<Object> left = field(name(previousWith.getName(), previousWith.field(0).getName()));
                Field<Object> right = field(name(nextWith.getName(), nextWith.field(0).getName()));
                iteratorSelect = iteratorSelect.innerJoin(nextWith).on(left.eq(right));
            }
            if (Objects.nonNull(iteratorSelect)) {
                Select finalSelect = iteratorSelect;
                Name currentTableWithName = name("final_join_query");
                CommonTableExpression finalQuery = currentTableWithName.as(finalSelect);
                totalWiths.add(finalQuery);
                // 最终处理当前节点的所有with
                WithStep withStep = dsl.with(totalWiths.stream().filter(Objects::nonNull).toArray(CommonTableExpression[]::new));
                query = withStep.select().from(finalQuery);
                if (Objects.nonNull(query)) {
                    System.out.println("我的with sql语句：\n" + dsl.renderInlined(query));
                }
            }
        }
    }
}
