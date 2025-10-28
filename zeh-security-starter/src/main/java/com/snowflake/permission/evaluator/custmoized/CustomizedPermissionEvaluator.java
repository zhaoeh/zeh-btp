package com.snowflake.permission.evaluator.custmoized;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义的权限评估器
 */
@Slf4j
public class CustomizedPermissionEvaluator<T> {

    private final CustomizedPermissionStrategyFactory<T> factory;

    public CustomizedPermissionEvaluator(CustomizedPermissionStrategyFactory<T> factory) {
        this.factory = factory;
    }

    public boolean hasCp(String permission, Object p1) {
        return factory.hasCustomizedPermissions(permission, toObjects(p1));
    }

    public boolean hasCp2(String permission, Object p1, Object p2) {
        return factory.hasCustomizedPermissions(permission, toObjects(p1, p2));
    }

    public boolean hasCp3(String permission, Object p1, Object p2, Object p3) {
        return factory.hasCustomizedPermissions(permission, toObjects(p1, p2, p3));
    }

    public boolean hasCp4(String permission, Object p1, Object p2, Object p3, Object p4) {
        return factory.hasCustomizedPermissions(permission, toObjects(p1, p2, p3, p4));
    }

    public boolean hasCp5(String permission, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return factory.hasCustomizedPermissions(permission, toObjects(p1, p2, p3, p4, p5));
    }

    public boolean hasCp6(String permission, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return factory.hasCustomizedPermissions(permission, toObjects(p1, p2, p3, p4, p5, p6));
    }

    private Object[] toObjects(Object... params) {
        return params;
    }
}
