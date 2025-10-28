package com.snowflake.permission.evaluator.custmoized;

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
public class CustomizedPermissionStrategyFactory<T> extends CustomizedPermissionStrategyFactorySupport<T> implements PermissionEvaluator<T> {

    public CustomizedPermissionStrategyFactory(ObjectProvider<PermissionRulesConfigure<T>> permissionRulesConfigures) {
        super(permissionRulesConfigures);
    }

    @Override
    public boolean hasCustomizedPermissions(String permission, Object[] params) {
        if (CollectionUtils.isEmpty(customizedPermissionMap)) {
            // 调度了 @cp 但是没有提供规则，默认放行
            throw new IllegalArgumentException("使用权限注解 @PreAuthorize 但CustomizedPermission未提供动态权限规则");
        }
        if(StringUtils.isBlank(permission)){
            throw new IllegalArgumentException("使用权限注解 @PreAuthorize 但hasCustomizedPermissions未传递权限标志");
        }
        CustomizedPermission customizedPermission = customizedPermissionMap.get(permission);
        if (Objects.isNull(customizedPermission)) {
            throw new IllegalStateException("使用权限注解 @PreAuthorize 权限标志未匹配到规则器");
        }
        PermissionDesc permissionResult = customizedPermission.hasPermissions(params);
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
