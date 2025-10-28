package com.dynamic.model.core;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.dynamic.model.config.ModelProperties;
import com.dynamic.model.context.ModelContextHolder;
import com.dynamic.model.enums.BizModel;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DynamicTableNameHandler implements TableNameHandler {

    private final Set<String> ignoreTables = new HashSet<>();

    public DynamicTableNameHandler(ModelProperties properties) {
        // 不同 DB 下，大小写的习惯不同，所以需要都添加进去
        properties.getIgnoreTables().forEach(table -> {
            ignoreTables.add(table.toLowerCase());
            ignoreTables.add(table.toUpperCase());
        });
        // 在 OracleKeyGenerator 中，生成主键时，会查询这个表，忽略该表作为动态表名候选
        ignoreTables.add("DUAL");
    }

    @Override
    public String dynamicTableName(String sql, String tableName) {

        // 需要忽略动态路由的请求url或者表，直接返回原始表名
        if (ModelContextHolder.isIgnore() || ignoreTables.contains(tableName)) {
            return tableName;
        }

        // 获取上下文模式
        BizModel model = ModelContextHolder.get();

        if (Objects.nonNull(model)) {
            if (Objects.equals(model, BizModel.BLEND)) {
                throw new IllegalArgumentException("动态表名拦截，上下文 ModelContextHolder 对应 model 不能是：BizModel.BLEND");
            }
            if (Objects.equals(model, BizModel.CA)) {
                // CA模式，走原始表名；原始表默认即为CA模式
                return tableName;
            }
            return model.name().toLowerCase() + "_" + tableName;
        }

        // 上下文model为null，走原始表名；此拦截器偏向底层，不做上下文模式校验，只做动态表名路由转发
        // 对于上下文请求模式的校验，应该由靠近客户端请求侧的上游应用组件进行，而不应该在此处进行
        return tableName;
    }
}
