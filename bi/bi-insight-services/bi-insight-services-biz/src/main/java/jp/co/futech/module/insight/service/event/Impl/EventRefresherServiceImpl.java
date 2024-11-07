package jp.co.futech.module.insight.service.event.Impl;

import cn.hutool.core.collection.CollectionUtil;
import jp.co.futech.framework.mybatis.core.query.LambdaQueryWrapperX;
import jp.co.futech.module.insight.constant.SqlTemplateConstant;
import jp.co.futech.module.insight.entity.ms.EventTaskDimMetaInfoEntity;
import jp.co.futech.module.insight.mapper.ms.EventTaskDimMetaInfoMapper;
import jp.co.futech.module.insight.sqltemplate.template.EventSqlTemplate;
import jp.co.futech.module.insight.sqltemplate.core.EventSqlTemplateRefresher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-07-25
 **/
@Component
public class EventRefresherServiceImpl implements EventSqlTemplateRefresher {

    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("{", "}");

    private Map<String, Map<String, EventTaskDimMetaInfoEntity>> metaInfoMap;

    private final EventTaskDimMetaInfoMapper eventTaskDimMetaInfoMapper;

    public EventRefresherServiceImpl(EventTaskDimMetaInfoMapper eventTaskDimMetaInfoMapper) {
        this.eventTaskDimMetaInfoMapper = eventTaskDimMetaInfoMapper;
        initMetaInfos();
    }

    /**
     * 初始化元数据信息
     */
    private void initMetaInfos() {
        List<EventTaskDimMetaInfoEntity> metaInfos = eventTaskDimMetaInfoMapper.selectList(new LambdaQueryWrapperX<>());
        if (CollectionUtil.isNotEmpty(metaInfos)) {
            metaInfoMap = metaInfos.stream().collect(Collectors.groupingBy(EventTaskDimMetaInfoEntity::getColTableName, Collectors.filtering(e -> shouldBeCollect(e.getColRename()), Collectors.toMap(EventTaskDimMetaInfoEntity::getColRename, Function.identity()))));
        }
    }

    @Override
    public EventSqlTemplate refresh(EventSqlTemplate template) {
        EventSqlTemplate.SqlMetaData sqlMetaData = template.getSqlMetaData();
        Optional.ofNullable(sqlMetaData).ifPresent(m -> {
            List<EventSqlTemplate.SqlMetaData.CalcSql> calcSqls = m.getCalcSql();
            refreshCalcSql(calcSqls);
            List<EventSqlTemplate.Dimension> dimension = m.getDimension();
            refreshDimension(dimension);
            EventSqlTemplate.FilterData filterData = m.getFilterData();
            refreshFilterData(filterData);
        });
        return template;
    }

    private void refreshCalcSql(List<EventSqlTemplate.SqlMetaData.CalcSql> calcSqls) {
        if (CollectionUtil.isNotEmpty(calcSqls)) {
            calcSqls.stream().forEach(calcSql -> {
                String tableName = calcSql.getColTableName();
                calcSql.setUserIdField(obtainRealColName(tableName, SqlTemplateConstant.USER_ID));
                calcSql.setDtField(obtainRealColName(tableName, SqlTemplateConstant.DATE_TIME));

                String colCalculation = calcSql.getColCalculation();
                calcSql.setColCalculation(placeholderHelper.replacePlaceholders(colCalculation, s -> {
                    if ("key".equals(s)) {
                        return calcSql.getColName();
                    }
                    if ("table_name".equals(s)) {
                        return tableName;
                    }
                    if (shouldBeCollect(s)) {
                        return obtainRealColName(tableName, s);
                    }
                    throw new IllegalArgumentException("json非法");
                }));

                List<EventSqlTemplate.SqlMetaData.CalcSql.FiltersCol> filtersCol = calcSql.getFiltersCol();
                refreshFiltersCol(filtersCol);
            });
        }
    }

    /**
     * 刷新 filtersCol
     *
     * @param filtersCol
     */
    private void refreshFiltersCol(List<EventSqlTemplate.SqlMetaData.CalcSql.FiltersCol> filtersCol) {
        if (CollectionUtil.isNotEmpty(filtersCol)) {
            filtersCol.stream().forEach(filter -> {
                String beforeRelation = filter.getBeforeRelation();
                String tableName = filter.getFilterColTableName();
                filter.setUserIdField(obtainRealColName(tableName, SqlTemplateConstant.USER_ID));
                filter.setDtField(obtainRealColName(tableName, SqlTemplateConstant.DATE_TIME));
            });
        }
    }

    private void refreshDimension(List<EventSqlTemplate.Dimension> dimension) {
        if (CollectionUtil.isNotEmpty(dimension)) {
            dimension.stream().forEach(e -> {
                String cleRename = e.getColRename();
                if (shouldBeCollect(cleRename)) {
                    e.setColName(obtainRealColName(e.getColTableName(), cleRename));
                }
            });
        }
    }

    private void refreshFilterData(EventSqlTemplate.FilterData filterData) {
        if (Objects.nonNull(filterData)) {
            List<EventSqlTemplate.FilterData.Filter> filters = filterData.getFilters();
            if (CollectionUtil.isNotEmpty(filters)) {
                filters.stream().forEach(e -> {
                    String cleRename = e.getFilterColRename();
                    if (shouldBeCollect(cleRename)) {
                        e.setFilterColName(obtainRealColName(e.getFilterColTableName(), cleRename));
                    }
                });
            }

        }
    }

    /**
     * 是否应该被收集
     *
     * @param source
     * @return
     */
    private boolean shouldBeCollect(String source) {
        return SqlTemplateConstant.USER_ID.equals(source) || SqlTemplateConstant.DATE_TIME.equals(source);
    }

    private String obtainRealColName(String tableName, String colRename) {
        if (CollectionUtil.isEmpty(metaInfoMap)) {
            initMetaInfos();
        }
        Assert.notEmpty(metaInfoMap, "metaInfoMap cannot be empty");
        Map<String, EventTaskDimMetaInfoEntity> currentTableInfos = metaInfoMap.get(tableName);
        Assert.notEmpty(currentTableInfos, String.format("currentTableInfos cannot be empty,tableName is %s", tableName));
        return Optional.ofNullable(currentTableInfos.get(colRename)).map(EventTaskDimMetaInfoEntity::getColName).orElseThrow(() -> new IllegalStateException("task_dim_meta_info no my data"));
//        return currentTableInfos.stream().filter(e -> e.getColRename().equals(colRename)).findFirst().map(EventTaskDimMetaInfoEntity::getColName).orElseThrow(() -> new IllegalStateException("task_dim_meta_info no my data"));
    }
}
