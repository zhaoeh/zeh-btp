package com.snowflake.hook;

import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface ApiOfflineProcessor {

    /**
     * 提供Offline下线接口清单
     * @return
     */
    Map<HttpMethod, List<String>> supplyOfflineList();
}
