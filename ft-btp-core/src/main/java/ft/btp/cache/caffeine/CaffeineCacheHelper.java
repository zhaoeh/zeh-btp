package ft.btp.cache.caffeine;

import ft.btp.common.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 本地缓存helper类
 *
 * @description: 本地缓存helper
 * @author: Erhu.Zhao
 * @create: 2023-10-17 18:34
 **/
@Slf4j
public class CaffeineCacheHelper {

    private static CaffeineCacheManager caffeineCacheManager;

    protected static void setCacheManager(CaffeineCacheManager caffeineCacheManager) {
        CaffeineCacheHelper.caffeineCacheManager = caffeineCacheManager;
    }

    /**
     * 在cacheName指定的cache缓存中获取key对应的缓存值
     *
     * @param cacheName local cache name
     * @param key       key
     * @param <T>       返回值类型
     * @return 指定key对应的value
     */
    public static <T> T getData(String cacheName, String key) {
        Assert.notNull(caffeineCacheManager,"caffeineCacheManager cannot be null");
        Cache cache = caffeineCacheManager.getCache(cacheName);
        if (Objects.isNull(cache)) {
            log.info("本地缓存cacheName-[{}]不存在", cacheName);
            return null;
        } else {
            Cache.ValueWrapper wrapper = cache.get(key);
            if (Objects.isNull(wrapper)) {
                log.info("本地缓存cacheName-[{}],key-[{}]不存在", cacheName, key);
                return null;
            } else {
                return (T) wrapper.get();
            }
        }
    }

    /**
     * 在默认名称的local cache中查找指定key名称对应的value
     *
     * @param key 缓存key
     * @param <T> value泛型
     * @return value
     */
    public static <T> T getData(String key) {
        return getData(CommonConstants.CAFFEINE_CACHE_NAME, key);
    }
}
