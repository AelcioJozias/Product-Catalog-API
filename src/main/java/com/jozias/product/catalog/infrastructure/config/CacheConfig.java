package com.jozias.product.catalog.infrastructure.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    private static final int CACHE_MAX_SIZE = 1000;
    private static final int CACHE_EXPIRE_MINUTES = 10;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("products", "productDetails");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(CACHE_EXPIRE_MINUTES))
                .maximumSize(CACHE_MAX_SIZE)
                .recordStats());
        return cacheManager;
    }
}
