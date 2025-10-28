//package com.snowflake.cache.core;
//
//import com.alicp.jetcache.Cache;
//
//import java.util.Map;
//import java.util.concurrent.*;
//
//public class RefreshScheduler {
//
//    private final ScheduledExecutorService executor =
//            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
//
//    private final Map<String, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();
//
//    public <K, V> void scheduleRefresh(Cache<K, V> cache,
//                                       K key,
//                                       Callable<V> loader,
//                                       long refreshIntervalSeconds) {
//        String taskKey = cache.getName() + ":" + key;
//
//        ScheduledFuture<?> task = executor.scheduleWithFixedDelay(() -> {
//            try {
//                if (cache.containsKey(key)) {
//                    V newValue = loader.call();
//                    cache.put(key, newValue);
//                } else {
//                    cancel(taskKey);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }, refreshIntervalSeconds, refreshIntervalSeconds, TimeUnit.SECONDS);
//
//        tasks.put(taskKey, task);
//    }
//
//    public void cancel(String taskKey) {
//        ScheduledFuture<?> task = tasks.remove(taskKey);
//        if (task != null) {
//            task.cancel(false);
//        }
//    }
//
//    public void shutdown() {
//        executor.shutdown();
//    }
//}
