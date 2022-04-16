package com.sciatta.openmall.cache.support;

import com.sciatta.openmall.cache.Cache;
import com.sciatta.openmall.cache.CacheChildKey;
import com.sciatta.openmall.cache.CacheExtend;
import com.sciatta.openmall.cache.CacheProcessor;
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
 * CacheAccessAspect
 */
@Slf4j
@Aspect
@Component
public class CacheAccessAspect implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Around("@annotation(com.sciatta.openmall.cache.Cache) && @annotation(cache)")
    public Object process(ProceedingJoinPoint pjp, Cache cache) {

        // 获得CacheProcessor
        CacheProcessor cacheProcessor = applicationContext.getBean(cache.processor(), CacheProcessor.class);
        if (ObjectUtils.isEmpty(cacheProcessor)) {
            log.error("Not obtained cacheProcessor: " + cache.processor());
            throw new IllegalArgumentException("Not obtained cacheProcessor: " + cache.processor());
        }
        log.debug("obtain cache processor: " + cache.processor());

        // 解析方法参数
        ResolveParameterWrap resolveParameterWrap = resolveParameter(pjp, cache);

        String key = resolveParameterWrap.key;

        // 获取缓存
        String value = cacheProcessor.tryHit(key);

        // 命中
        if (StringUtils.hasText(value)) {
            return cacheProcessor.hit(key, value, cache, resolveParameterWrap.extend);
        }

        // 未命中，回源执行目标方法
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            log.error("execute target method error: " + e.getMessage());
            throw new IllegalAccessError("execute target method error: " + e.getMessage());
        }

        // 回源后设置缓存
        return cacheProcessor.afterReturnFromSource(key, result, cache, resolveParameterWrap.extend);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // private

    private ResolveParameterWrap resolveParameter(ProceedingJoinPoint pjp, Cache cache) {

        ResolveParameterWrap resolveParameterWrap = new ResolveParameterWrap();

        List<ChildKeyWrap> childKeyWraps = new ArrayList<>();
        List<Object> cacheExtends = new ArrayList<>();

        Object[] args = pjp.getArgs();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        int parameterCount = signature.getMethod().getParameterCount();

        // 方法的参数列表，以及参数上的若干注解
        Annotation[][] parameterAnnotations = signature.getMethod().getParameterAnnotations();
        // 解析方法上的参数
        for (int i = 0; i < parameterCount; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            for (Annotation annotation : annotations) {
                // 解析CacheChildKey
                if (annotation instanceof CacheChildKey) {
                    ChildKeyWrap childKeyWrap = new ChildKeyWrap();
                    childKeyWrap.index = i;
                    childKeyWrap.cacheChildKey = (CacheChildKey) annotation;
                    childKeyWrap.arg = args[i];

                    childKeyWraps.add(childKeyWrap);
                }

                // 解析CacheExtend
                if (annotation instanceof CacheExtend) {
                    cacheExtends.add(args[i]);
                }
            }
        }

        childKeyWraps.sort(ChildKeyWrap::compareTo);

        resolveParameterWrap.key = generateKey(cache, childKeyWraps);   // 生产key，CacheChildKey使得key更有区分度
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
