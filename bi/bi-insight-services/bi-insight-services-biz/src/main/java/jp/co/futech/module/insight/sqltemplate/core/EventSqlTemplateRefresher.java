package jp.co.futech.module.insight.sqltemplate.core;

import jp.co.futech.module.insight.sqltemplate.template.EventSqlTemplate;

/**
 * @description: 事件SQL模板刷新器
 * @author: ErHu.Zhao
 * @create: 2024-07-25
 **/
@FunctionalInterface
public interface EventSqlTemplateRefresher {

    /**
     * 给客户端一个机会去刷新模板对象
     *
     * @param template sql模板对象
     * @return 刷新后的模板对象
     */
    EventSqlTemplate refresh(EventSqlTemplate template);
}
