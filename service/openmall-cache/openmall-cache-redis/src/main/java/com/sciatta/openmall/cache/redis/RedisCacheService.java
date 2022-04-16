package com.sciatta.openmall.cache.redis;

import com.sciatta.openmall.cache.CacheService;
import com.sciatta.openmall.common.constants.CacheConstants;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rain on 2022/3/16<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * RedisCacheService
 */
public class RedisCacheService implements CacheService {
    private final StringRedisTemplate redisTemplate;

    public RedisCacheService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void set(String key, String value, long timeout) {
        set(key, value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        if (timeout == CacheConstants.NEVER_EXPIRE) {
            set(key, value);    // 永不过期
            return;
        }

        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public List<String> multiGet(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    @Override
    public void expire(String key, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    public void expire(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }
}
