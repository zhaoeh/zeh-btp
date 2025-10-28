package com.snowflake.permission.evaluator;

import org.apache.commons.lang3.StringUtils;

/**
 * 权限评估器接口
 */
public interface PermissionEvaluator<T> {

    /**
     * 获取当前认证用户
     * @return 当前经过认证的用户
     */
    T obtainLogin();

    /**
     * 是否具备目标资源操作权限
     *
     * @param permission 权限标记符号
     * @return 是否具备目标资源操作权限 true:具备 false:不具备
     */
    default boolean hasPermissions(String permission) {
        if (StringUtils.isBlank(permission)) {
            throw new IllegalStateException("使用权限注解 @PreAuthorize 但hasPermissions未传递权限标志");
        }
        return checkHasPermissions(permission, obtainLogin());
    }

    /**
     * 是否具备动态权限
     *
     * @param permission
     * @param params
     * @return
     */
    default boolean hasDynamicPermissions(String permission, Object[] params) {
        if (StringUtils.isBlank(permission)) {
            throw new IllegalStateException("使用权限注解 @PreAuthorize 但hasDynamicPermissions未传递权限标志");
        }
        return checkHasDynamicPermissions(permission, obtainLogin(), params);
    }

    /**
     * 是否具备自定义权限
     * @param permission
     * @param params
     * @return
     */
    default boolean hasCustomizedPermissions(String permission, Object[] params) {
        if (StringUtils.isBlank(permission)) {
            throw new IllegalStateException("使用权限注解 @PreAuthorize 但hasCustomizedPermissions未传递权限标志");
        }
        return checkHasCustomizedPermissions(permission, obtainLogin(), params);
    }


    /**
     * 默认实现，无权限操作
     *
     * @param permission 权限标记符
     * @param login 经过认证的当前登录者
     * @return 是否具备目标资源操作权限
     */
    default boolean checkHasPermissions(String permission, T login) {
        return false;
    }

    /**
     * 默认实现，无权限操作
     *
     * @param permission 权限标记符
     * @param login 经过认证的当前登录者
     * @return 是否具备目标资源操作权限
     */
    default boolean checkHasDynamicPermissions(String permission, T login, Object[] params) {
        return false;
    }

    /**
     * 默认实现，无权限操作
     *
     * @param permission 权限标记符
     * @param login 经过认证的当前登录者
     * @return 是否具备目标资源操作权限
     */
    default boolean checkHasCustomizedPermissions(String permission, T login, Object[] params) {
        return false;
    }
}
