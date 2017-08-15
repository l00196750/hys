package com.hys.common.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class SpringCaffeineCacheManager {

    /**
     * 创建Caffeine缓存.
     */
    public Map<String, Caffeine<Object, Object>> createCaffeineCache() {
        Map<String, Caffeine<Object, Object>> cacheConfig = Maps.newLinkedHashMap();

        cacheConfig.put("getUserByName", Caffeine.newBuilder().expireAfterAccess(60, TimeUnit.SECONDS));
        return cacheConfig;
    }

    // @Bean
    // @Primary
    //
    /**
     * 需要使用再打开.
     */
    public CacheManager caffeineCacheManager() {
        Map<String, Caffeine<Object, Object>> cacheConfig = createCaffeineCache();

        ArrayList<CaffeineCache> caches = new ArrayList<CaffeineCache>();
        for (Entry<String, Caffeine<Object, Object>> entry : cacheConfig.entrySet()) {
            caches.add(new CaffeineCache(entry.getKey(), entry.getValue().build()));
        }

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
