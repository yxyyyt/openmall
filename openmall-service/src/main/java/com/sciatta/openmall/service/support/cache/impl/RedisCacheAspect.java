package com.sciatta.openmall.service.support.cache.impl;

import com.sciatta.openmall.service.support.cache.Cache;
import com.sciatta.openmall.service.support.cache.CacheChildKey;
import com.sciatta.openmall.service.support.cache.CacheExtend;
import com.sciatta.openmall.service.support.cache.CacheProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class RedisCacheAspect implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    
    @Around("@annotation(com.sciatta.openmall.service.support.cache.Cache) && @annotation(cache)")
    public Object set(ProceedingJoinPoint pjp, Cache cache) throws Throwable {
        
        // 获得CacheProcessor
        CacheProcessor cacheProcessor = applicationContext.getBean(cache.processor(), CacheProcessor.class);
        if (ObjectUtils.isEmpty(cacheProcessor)) {
            log.error("Not obtained cacheProcessor: " + cache.processor());
            throw new Throwable("Not obtained cacheProcessor: " + cache.processor());
        }
        log.debug("obtain cache processor: " + cache.processor());
        
        // 解析方法参数
        ResolveParameterWrap resolveParameterWrap = resolveParameter(pjp, cache);
        
        String key = resolveParameterWrap.key;
        
        // 获取缓存
        String value = (String) cacheProcessor.hit(key);
        
        // 命中处理
        if (StringUtils.hasText(value)) {
            return cacheProcessor.hitProcess(key, value, cache, resolveParameterWrap.extend);
        }
        
        // 未命中执行目标方法
        Object result = pjp.proceed();
        
        // 未命中处理
        return cacheProcessor.missProcess(key, result, cache, resolveParameterWrap.extend);
    }
    
    private ResolveParameterWrap resolveParameter(ProceedingJoinPoint pjp, Cache cache) {
        
        ResolveParameterWrap resolveParameterWrap = new ResolveParameterWrap();
        
        List<ChildKeyWrap> childKeyWraps = new ArrayList<>();
        List<Object> cacheExtends = new ArrayList<>();
        
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
                }
                
                if (annotation instanceof CacheExtend) {
                    cacheExtends.add(args[i]);
                }
            }
        }
        
        childKeyWraps.sort(ChildKeyWrap::compareTo);
        
        resolveParameterWrap.key = generateKey(cache, childKeyWraps);
        resolveParameterWrap.extend = cacheExtends.toArray();
        
        return resolveParameterWrap;
    }
    
    private String generateKey(Cache cache, List<ChildKeyWrap> childKeyWraps) {
        StringBuilder sb = new StringBuilder(cache.key());
        for (ChildKeyWrap childKeyWrap : childKeyWraps) {
            sb.append(":").append(childKeyWrap.arg);
        }
        return sb.toString();
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
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
    
    private static class ResolveParameterWrap {
        String key;
        Object[] extend;
    }
}
