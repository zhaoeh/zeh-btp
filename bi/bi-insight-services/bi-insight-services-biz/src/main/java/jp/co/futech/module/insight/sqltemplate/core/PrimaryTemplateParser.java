package jp.co.futech.module.insight.sqltemplate.core;

import cn.hutool.core.collection.CollUtil;
import jp.co.futech.module.insight.constant.SqlTemplateConstant;
import jp.co.futech.module.insight.sqltemplate.template.EventSqlExpression;
import jp.co.futech.module.insight.sqltemplate.template.EventSqlTemplate;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;

/**
 * @description: 主节点解析器
 * @author: ErHu.Zhao
 * @create: 2024-07-29
 **/
@Component
public class PrimaryTemplateParser {

    private final String AGREE = "agr";

    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("{", "}");

    @Autowired
    private DSLContext dsl;

    @Autowired
    private ClauseProcessor clauseProcessor;

    @Autowired
    private FunctionProcessor functionProcessor;

    /**
     * 处理主节点
     *
     * @param calcSql
     * @param count
     * @return
     */
    public EventSqlExpression.RootExpression processPrimaryNode(EventSqlTemplate.SqlMetaData.CalcSql calcSql,
                                                                List<EventSqlTemplate.SqlMetaData.CalcSql.FiltersCol> primaryConditions,
                                                                List<EventSqlTemplate.Dimension> groupings,
                                                                EventSqlTemplate.FilterData appendPrimaryConditions,
                                                                AtomicInteger count) {
        String currentField = calcSql.getColCalculation();
        currentField = placeholderHelper.replacePlaceholders(currentField, s -> calcSql.getColName());
        String primaryTableName = calcSql.getColTableName();
        // 主表with别名
        String primaryWithAliasName = primaryTableName + "_" + count.get();
        Name primaryWithName = name(primaryWithAliasName);
        List<Field<?>> selectField = new ArrayList<>();
        selectField.add(field(calcSql.getUserIdField()).as(SqlTemplateConstant.LOGIN_NAME));
        selectField.add(functionProcessor.toDate(field(calcSql.getDtField())).as(SqlTemplateConstant.DT));
        selectField.add(field(currentField).as(AGREE + count.get()));
        List<Field<?>> groupFields = null;
        if (CollUtil.isNotEmpty(groupings)) {
            groupFields = groupings.stream().map(group -> field(name(group.getColName()))).collect(Collectors.toList());
            selectField.addAll(groupFields);
        }
        SelectJoinStep selectJoinStep = dsl.select(selectField).from(table(primaryTableName));
        Select primaryQuery = selectJoinStep;

        primaryQuery = clauseProcessor.processWhere(primaryQuery, selectJoinStep, primaryConditions, appendPrimaryConditions);

        clauseProcessor.processGroupBy((SelectJoinStep) primaryQuery, groupFields);
        // 构建主表with表达式
        CommonTableExpression with = primaryWithName.as(primaryQuery);
        EventSqlExpression.RootExpression rootExpression = new EventSqlExpression.RootExpression();
        rootExpression.setSelfExpression(with);
        return rootExpression;
    }


}
