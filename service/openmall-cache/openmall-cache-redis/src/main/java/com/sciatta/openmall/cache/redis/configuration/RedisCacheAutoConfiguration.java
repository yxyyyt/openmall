package com.sciatta.openmall.cache.redis.configuration;

import com.sciatta.openmall.cache.CacheProcessor;
import com.sciatta.openmall.cache.CacheService;
import com.sciatta.openmall.cache.redis.RedisCacheProcessor;
import com.sciatta.openmall.cache.redis.RedisCacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by Rain on 2022/3/16<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * RedisCacheAutoConfiguration
 */
@Configuration
public class RedisCacheAutoConfiguration {
    @Bean
    @ConditionalOnClass(RedisCacheService.class)
    public CacheService redisCacheService(StringRedisTemplate redisTemplate) {
        return new RedisCacheService(redisTemplate);
    }

    @Bean
    @ConditionalOnClass(RedisCacheProcessor.class)
    public CacheProcessor redisCacheProcessor(CacheService cacheService) {
        return new RedisCacheProcessor(cacheService);
    }
}
