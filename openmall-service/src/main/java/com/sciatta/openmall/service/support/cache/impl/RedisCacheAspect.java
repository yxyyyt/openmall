package com.sciatta.openmall.service.support.cache.impl;

import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.service.support.cache.Cache;
import com.sciatta.openmall.service.support.cache.CacheService;
import com.sciatta.openmall.service.support.cache.CacheChildKey;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
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
    private final CacheService cacheService;
    
    public RedisCacheAspect(CacheService cacheService) {
        this.cacheService = cacheService;
    }
    
    @Around("@annotation(com.sciatta.openmall.service.support.cache.Cache) && @annotation(cache)")
    public Object setCache(ProceedingJoinPoint pjp, Cache cache) throws Throwable {
        // 解析生成key
        String key = resolveKey(pjp, cache);
        
        Class<?> toClass = cache.toClass();
        boolean isList = cache.isList();
        long timeout = cache.timeout();
        long invalidTimeout = cache.invalidTimeout();
        
        // 获取缓存
        String value = cacheService.get(key);
        
        // 命中
        if (StringUtils.hasText(value)) {
            log.debug("get cache {} = {}", key, value);
            
            Object data;
            if (isList) {
                data = JsonUtils.jsonToList(value, toClass);
            } else {
                data = JsonUtils.jsonToPojo(value, toClass);
            }
            return JSONResult.success(data);
        }
        
        // 执行目标方法
        JSONResult jsonResult = (JSONResult) pjp.proceed();
        
        // 设置缓存
        value = JsonUtils.objectToJson(jsonResult.getData());
        if (ObjectUtils.isEmpty(jsonResult.getData())) {
            // 防止缓存穿透恶意攻击
            cacheService.set(key, value, invalidTimeout);
            log.warn("set invalid cache {} = {}", key, value);
        } else {
            cacheService.set(key, JsonUtils.objectToJson(jsonResult.getData()), timeout);
            log.debug("set cache {} = {}", key, value);
        }
        
        return jsonResult;
    }
    
    private String resolveKey(ProceedingJoinPoint pjp, Cache cache) {
        List<ChildKeyWrap> childKeyWraps = new ArrayList<>();
        
        Object[] args = pjp.getArgs();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        int parameterCount = signature.getMethod().getParameterCount();
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        
        for (int i = 0; i < parameterCount; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            for (Annotation annotation : annotations) {
                if (annotation instanceof CacheChildKey) {
                    ChildKeyWrap childKeyWrap = new ChildKeyWrap();
                    childKeyWrap.index = i;
                    childKeyWrap.cacheChildKey = (CacheChildKey) annotation;
                    childKeyWrap.arg = args[i];
                    
                    childKeyWraps.add(childKeyWrap);
                    break;
                }
            }
        }
        
        childKeyWraps.sort(ChildKeyWrap::compareTo);
        
        return generateKey(cache, childKeyWraps);
    }
    
    private String generateKey(Cache cache, List<ChildKeyWrap> childKeyWraps) {
        StringBuilder sb = new StringBuilder(cache.key());
        for (ChildKeyWrap childKeyWrap : childKeyWraps) {
            sb.append(":").append(childKeyWrap.arg);
        }
        return sb.toString();
    }
    
    
    private static class ChildKeyWrap implements Comparable<ChildKeyWrap> {
        int index;
        CacheChildKey cacheChildKey;
        Object arg;
        
        @Override
        public int compareTo(ChildKeyWrap o) {
            return cacheChildKey.order() - o.cacheChildKey.order();
        }
    }
}
