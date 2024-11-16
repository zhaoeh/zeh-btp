package zeh.btp.scope.core;

import zeh.btp.scope.po.BeanScopeManagerDefinition;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @description: 本地缓存器
 * @author: ErHu.Zhao
 * @create: 2024-08-27
 **/
class LocalCaches {

    private static Map<String, BeanScopeManagerDefinition> finallyManagerContainer;

    static void bindFinallyManagerContainer(Map<String, BeanScopeManagerDefinition> finallyManagerContainer) {
        if (CollectionUtils.isEmpty(finallyManagerContainer)) {
            return;
        }
        LocalCaches.finallyManagerContainer = finallyManagerContainer;
    }

    static Map<String, BeanScopeManagerDefinition> getFinallyManagerContainer() {
        return finallyManagerContainer;
    }
}
