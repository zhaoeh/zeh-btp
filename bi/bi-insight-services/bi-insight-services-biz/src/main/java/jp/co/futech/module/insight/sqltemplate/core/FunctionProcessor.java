package jp.co.futech.module.insight.sqltemplate.core;

import org.jooq.Field;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.function;


/**
 * @description: sql函数处理器
 * @author: ErHu.Zhao
 * @create: 2024-07-30
 **/
@Component
public class FunctionProcessor {

    /**
     * 对Field应用toDate函数
     *
     * @param field
     * @return
     */
    public Field<Object> toDate(Field<Object> field) {
        return function("toDate", Object.class, field);
    }

    /**
     * 原始返回
     *
     * @param field
     * @return
     */
    public Field<Object> original(Field<Object> field) {
        return field;
    }
}
