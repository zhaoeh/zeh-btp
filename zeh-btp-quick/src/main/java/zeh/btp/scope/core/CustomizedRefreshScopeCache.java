package zeh.btp.scope.core;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import zeh.btp.scope.hook.BeanScopeManager;
import zeh.btp.utils.CacheUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 定时刷新作用域范围缓存对象
 * @author: ErHu.Zhao
 * @create: 2024-08-21
 **/
@Slf4j
public class CustomizedRefreshScopeCache {

    private final Map<String, BeanScopeManager> beanScopeManagerMap = new ConcurrentHashMap<>();
    private final Map<String, LoadingCache<ScopeKey, Object>> customizedRefreshScopeCaches = new ConcurrentHashMap<>();
    private final Map<Long, LoadingCache<ScopeKey, Object>> customizedRefreshScopeCachesWithMinutes = new ConcurrentHashMap<>();


    /**
     * 初始化缓存器
     *
     * @param beanName
     * @param minutes
     */
    public void initCaches(String beanName, long minutes, @Nullable BeanScopeManager manager) {
        if (Objects.nonNull(manager)) {
            beanScopeManagerMap.put(beanName, manager);
        }
        if (minutes > 0) {
            LoadingCache<ScopeKey, Object> loadingCache = customizedRefreshScopeCachesWithMinutes.computeIfAbsent(minutes, this::buildLoadingCache);
            customizedRefreshScopeCaches.put(beanName, loadingCache);
        }
    }

    /**
     * 获取目标bean绑定的loading cache
     *
     * @param beanName
     * @return
     */
    public LoadingCache<ScopeKey, Object> obtainCache(String beanName) {
        return customizedRefreshScopeCaches.get(beanName);
    }

    /**
     * 获取目标bean绑定的BeanScopeManager
     *
     * @param beanName
     * @return
     */
    public BeanScopeManager obtainManager(String beanName) {
        return beanScopeManagerMap.get(beanName);
    }

    /**
     * 构建本地加载缓存对象
     *
     * @param minutes
     * @return
     */
    private LoadingCache<ScopeKey, Object> buildLoadingCache(long minutes) {
        return CacheUtils.buildAsyncReloadingCache(Duration.ofMinutes(minutes),
                new CacheLoader<>() {
                    @Override
                    public Object load(ScopeKey key) {
                        log.info("开始刷新scope作用域对象缓存，bean name is {}", key.getBeanName());
                        Object result = key.getObjectFactory().getObject();
                        log.info("结束刷新scope作用域对象缓存");
                        return result;
                    }
                });
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @Valid
    public static class ScopeKey {
        @EqualsAndHashCode.Include
        @NotBlank
        private String beanName;
        @NotNull
        private ObjectFactory<?> objectFactory;
    }
}
