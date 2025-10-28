package com.snowflake.permission.evaluator.custmoized;

import com.snowflake.permission.evaluator.PermissionDesc;

/**
 * 自定义权限接口，子类负责提供实现权限校验能力，权限工厂负责聚合权限标识和权限供应器，
 */
@FunctionalInterface
public interface CustomizedPermission {

    /**
     * 是否具备权限
     */
    PermissionDesc hasPermissions(Object[] params);

}
