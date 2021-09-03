package com.sciatta.openmall.api.support.cache;

import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.constants.RedisCacheConstants;
import com.sciatta.openmall.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/9/3<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * RedisCacheAspect
 */
@Slf4j
@Aspect
@Component
public class RedisCacheAspect {
    private final StringRedisTemplate redisTemplate;
    
    public RedisCacheAspect(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @Around("@annotation(com.sciatta.openmall.api.support.cache.Cache) && @annotation(cache)")
    public Object setCache(ProceedingJoinPoint pjp, Cache cache) throws Throwable {
        String key = cache.key();
        String value;
        Class<?> toClass = cache.toClass();
        boolean isList = cache.isList();
        
        // 从缓存中获取
        value = redisTemplate.opsForValue().get(key);
        if (StringUtils.hasText(value)) {
            log.debug("cache {} has value: {}", key, value);
            
            Object data;
            if (isList) {
                data = JsonUtils.jsonToList(value, toClass);
            } else {
                data = JsonUtils.jsonToPojo(value, toClass);
            }
            return JSONResult.success(data);
        }
        
        Object result = pjp.proceed();
        
        // 设置缓存
        if (result instanceof JSONResult) {
            JSONResult jsonResult = (JSONResult) result;
            value = JsonUtils.objectToJson(jsonResult.getData());
            redisTemplate.opsForValue().set(key, value);
            log.debug("set cache {} = {}", key, value);
        }
        
        return result;
    }
}
