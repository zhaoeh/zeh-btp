package com.snowflake.permission.evaluator.dynamic;


import com.snowflake.permission.evaluator.PermissionDesc;

/**
 * 动态权限接口，子类负责提供实现权限校验能力，权限工厂负责聚合权限标识和权限供应器，
 */
@FunctionalInterface
public interface DynamicPermission<T> {

    /**
     * 是否具备权限
     */
    PermissionDesc hasPermissions(T loginUser, Object[] params);

}
