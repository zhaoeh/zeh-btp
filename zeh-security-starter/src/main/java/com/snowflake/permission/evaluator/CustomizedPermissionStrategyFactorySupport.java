package com.snowflake.permission.evaluator;

import com.snowflake.permission.evaluator.custmoized.CustomizedPermission;
import com.snowflake.permission.evaluator.dynamic.DynamicPermission;
import com.snowflake.permission.evaluator.statics.StaticPermission;
import lombok.Data;
import org.springframework.beans.factory.ObjectProvider;

import java.util.Map;
import java.util.Objects;

@Data
public abstract class CustomizedPermissionStrategyFactorySupport<T> implements PermissionEvaluator<T> {

    protected Map<String, CustomizedPermission> customizedPermissionMap;

    protected Map<String, DynamicPermission<T>> dynamicPermissionMap;

    protected Map<String, StaticPermission<T>> staticPermissionMap;

    protected PermissionRulesConfigure<T> permissionRulesConfigure;

    public CustomizedPermissionStrategyFactorySupport(ObjectProvider<PermissionRulesConfigure<T>> permissionRulesConfigureObjectProvider) {

        permissionRulesConfigure = permissionRulesConfigureObjectProvider.getIfAvailable();

        if (Objects.nonNull(permissionRulesConfigure)) {
            customizedPermissionMap = permissionRulesConfigure.provideCustomizedPermissionRules();

            dynamicPermissionMap = permissionRulesConfigure.provideDynamicPermissionRules();

            staticPermissionMap = permissionRulesConfigure.provideStaticPermission();

        }

    }

    @Override
    public T obtainLogin() {
        if (Objects.nonNull(permissionRulesConfigure)) {
            return permissionRulesConfigure.obtainLogin();
        }
        return null;
    }
}
