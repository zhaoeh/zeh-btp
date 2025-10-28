package com.snowflake.permission.evaluator.statics;

/**
 * 静态权限评估器:权限评估策略只和当前登录人相关
 */
public class StaticPermissionEvaluator<T> {

    private final StaticPermissionStrategyFactory<T> factory;

    public StaticPermissionEvaluator(StaticPermissionStrategyFactory<T> factory) {
        this.factory = factory;
    }

    public boolean hasSp(String permission) {
        return factory.hasPermissions(permission);
    }

}
