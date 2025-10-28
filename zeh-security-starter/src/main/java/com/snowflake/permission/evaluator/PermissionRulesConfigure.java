package com.snowflake.permission.evaluator;

import com.snowflake.permission.evaluator.custmoized.CustomizedPermission;
import com.snowflake.permission.evaluator.dynamic.DynamicPermission;
import com.snowflake.permission.evaluator.statics.StaticPermission;

import java.util.HashMap;
import java.util.Map;

/**
 * 权限规则配置接口：具体实现由业务侧负责提供，不做强制要求，不实现即不做授权操作
 */
public interface PermissionRulesConfigure<T> {

    /**
     * 获取当前认证用户
     *
     * @return 当前经过认证的业务用户，用户权限评估
     */
    T obtainLogin();

    default Map<String, CustomizedPermission> provideCustomizedPermissionRules() {
        return new HashMap<>();
    }

    default Map<String, DynamicPermission<T>> provideDynamicPermissionRules() {
        return new HashMap<>();
    }

    default Map<String, StaticPermission<T>> provideStaticPermission() {
        return new HashMap<>();
    }

    /**
     * 授权结果后置处理，授权后、返回前由业务侧介入处理
     *
     * @param permission
     * @param permissionDesc
     */
    void postPermissionHandle(String permission, PermissionDesc permissionDesc);

}
