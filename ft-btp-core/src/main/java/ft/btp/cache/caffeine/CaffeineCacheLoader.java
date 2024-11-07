package ft.btp.cache.caffeine;

import java.util.function.Supplier;

/**
 * 本地缓存加载器接口
 *
 * @param <T>
 */
public interface CaffeineCacheLoader<T> {

    /**
     * 获取数据类别，一个分类标志对应一套缓存数据
     */
    String getDataCategory();

    /**
     * 获取数据供应者，缓存数据供应者，使用泛型支持通用，可以提供一个map或者其他java对象作为缓存数据
     */
    Supplier<T> getValueSupplier();
}
