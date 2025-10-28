package com.snowflake.permission.evaluator.statics;

import com.snowflake.permission.evaluator.CustomizedPermissionStrategyFactorySupport;
import com.snowflake.permission.evaluator.PermissionDesc;
import com.snowflake.permission.evaluator.PermissionEvaluator;
import com.snowflake.permission.evaluator.PermissionRulesConfigure;
import com.snowflake.utils.ServletUtils;
import com.snowflake.utils.WebFrameworkUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

/**
 * 静态权限策略工厂
 */
@Slf4j
public class StaticPermissionStrategyFactory<T> extends CustomizedPermissionStrategyFactorySupport<T> implements PermissionEvaluator<T> {

    public StaticPermissionStrategyFactory(ObjectProvider<PermissionRulesConfigure<T>> permissionRulesConfigures) {
        super(permissionRulesConfigures);
    }

    @Override
    public boolean checkHasPermissions(String permission, T login) {
        if (CollectionUtils.isEmpty(staticPermissionMap)) {
            // 调度了 @cp 但是没有提供规则，默认放行
            throw new IllegalArgumentException("使用权限注解 @PreAuthorize 但StaticPermission未提供动态权限规则");
        }
        if (StringUtils.isBlank(permission)) {
            throw new IllegalArgumentException("使用权限注解 @PreAuthorize 但hasPermissions未传递权限标志");
        }

        StaticPermission<T> staticPermission = staticPermissionMap.get(permission);
        if (Objects.isNull(staticPermission)) {
            throw new IllegalStateException("使用权限注解 @PreAuthorize 权限标志未匹配到规则器");
        }
        PermissionDesc permissionResult = staticPermission.hasPermissions(login);
        if (!BooleanUtils.isTrue(permissionResult.getResult())) {
            HttpServletRequest request = ServletUtils.getRequest();
            if (Objects.nonNull(request)) {
                WebFrameworkUtils.setAccessDinedMsg(request, "越权访问权限符：" + permission + "，禁止。原因：[" + permissionResult.getMsg() + "]");
            }
            permissionRulesConfigure.postPermissionHandle(permission, permissionResult);
            return false;
        }

        return true;
    }


}
