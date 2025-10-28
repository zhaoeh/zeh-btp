package com.snowflake.permission.evaluator.dynamic;

import lombok.extern.slf4j.Slf4j;

/**
 * 动态权限评估器：不仅依赖于当前认证用户的权限评估器，和请求参数有关，自定义，提高灵活度
 */
@Slf4j
public class DynamicPermissionEvaluator<T> {

    private final DynamicPermissionStrategyFactory<T> factory;

    public DynamicPermissionEvaluator(DynamicPermissionStrategyFactory<T> factory) {
        this.factory = factory;
    }

    public boolean hasDp(String permission, Object p1) {
        return factory.hasDynamicPermissions(permission, toObjects(p1));
    }

    public boolean hasDp2(String permission, Object p1, Object p2) {
        return factory.hasDynamicPermissions(permission, toObjects(p1, p2));
    }

    public boolean hasDp3(String permission, Object p1, Object p2, Object p3) {
        return factory.hasDynamicPermissions(permission, toObjects(p1, p2, p3));
    }

    public boolean hasDp4(String permission, Object p1, Object p2, Object p3, Object p4) {
        return factory.hasDynamicPermissions(permission, toObjects(p1, p2, p3, p4));
    }

    public boolean hasDp5(String permission, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return factory.hasDynamicPermissions(permission, toObjects(p1, p2, p3, p4, p5));
    }

    public boolean hasDp6(String permission, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return factory.hasDynamicPermissions(permission, toObjects(p1, p2, p3, p4, p5, p6));
    }

    private Object[] toObjects(Object... params) {
        return params;
    }
}
