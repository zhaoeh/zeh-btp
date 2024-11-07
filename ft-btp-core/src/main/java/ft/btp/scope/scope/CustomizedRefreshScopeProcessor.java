package ft.btp.scope.scope;

import com.google.common.cache.LoadingCache;
import ft.btp.scope.builder.DefaultIocBeanBuilder;
import ft.btp.scope.core.CustomizedRefreshScopeCache;
import ft.btp.scope.hook.BeanScopeManager;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 定时刷新对象作用域
 * @author: ErHu.Zhao
 * @create: 2024-08-20
 **/
public class CustomizedRefreshScopeProcessor implements Scope {

    public static final String CUSTOMIZED_REFRESH_SCOPE = "customized_refresh_scope";

    private final CustomizedRefreshScopeCache customizedRefreshScopeCache;

    private final Map<String, Object> objectMap = new ConcurrentHashMap<>();

    public CustomizedRefreshScopeProcessor(CustomizedRefreshScopeCache customizedRefreshScopeCache) {
        this.customizedRefreshScopeCache = customizedRefreshScopeCache;
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if (!ScopedProxyUtils.isScopedTarget(name)) {
            name = ScopedProxyUtils.getTargetBeanName(name);
        }
        Object targetObject = null;

        // 优先级1：走自定义处理器逻辑
        BeanScopeManager manager = customizedRefreshScopeCache.obtainManager(name);
        if (Objects.nonNull(manager)) {
            targetObject = manager.get(name, DefaultIocBeanBuilder.builder().factory(objectFactory).build());
        }
        if (Objects.nonNull(targetObject)) {
            return targetObject;
        }

        // 优先级2：走cache缓存刷新
        LoadingCache<CustomizedRefreshScopeCache.ScopeKey, Object> cache = customizedRefreshScopeCache.obtainCache(name);
        if (Objects.nonNull(cache)) {
            CustomizedRefreshScopeCache.ScopeKey scopeKey = CustomizedRefreshScopeCache.ScopeKey.builder().beanName(name).objectFactory(objectFactory).build();
            targetObject = cache.getIfPresent(scopeKey);
            if (Objects.isNull(targetObject)) {
                targetObject = objectMap.computeIfAbsent(name, t -> objectFactory.getObject());
                cache.put(scopeKey, targetObject);
            }
            if (Objects.nonNull(targetObject)) {
                return targetObject;
            }
        }

        // 优先级3：走内部单例缓存
        targetObject = objectMap.computeIfAbsent(name, t -> objectFactory.getObject());
        return targetObject;
    }

    @Override
    public Object remove(String name) {
        BeanScopeManager manager = customizedRefreshScopeCache.obtainManager(name);
        if (Objects.nonNull(manager)) {
            return manager.remove(name);
        }
        LoadingCache<CustomizedRefreshScopeCache.ScopeKey, Object> cache = customizedRefreshScopeCache.obtainCache(name);
        if (Objects.nonNull(cache)) {
            Map<CustomizedRefreshScopeCache.ScopeKey, Object> map = cache.asMap();
            CustomizedRefreshScopeCache.ScopeKey key = CustomizedRefreshScopeCache.ScopeKey.builder().beanName(name).build();
            if (map.containsKey(key)) {
                return cache.asMap().remove(key);
            }
        }
        if (objectMap.containsKey(name)) {
            return objectMap.remove(name);
        }
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return "customized_refresh_scope";
    }

}
