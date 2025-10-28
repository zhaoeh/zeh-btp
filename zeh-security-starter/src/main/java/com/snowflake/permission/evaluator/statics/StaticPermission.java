package com.snowflake.permission.evaluator.statics;


import com.snowflake.permission.evaluator.PermissionDesc;

/**
 * 静态权限接口，子类负责提供实现权限校验能力，权限工厂负责聚合权限标识和权限供应器，
 */
@FunctionalInterface
public interface StaticPermission<T> {

    /**
     * 是否具备权限
     */
    PermissionDesc hasPermissions(T loginUser);

}
