package com.sciatta.openmall.api.support.cache;

import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.service.support.cache.Cache;
import com.sciatta.openmall.service.support.cache.CacheProcessor;
import com.sciatta.openmall.service.support.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

/**
 * Created by yangxiaoyu on 2021/9/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * AbstractCacheProcess
 */
@Slf4j
public abstract class AbstractCacheProcessor implements CacheProcessor {
    protected final CacheService cacheService;
    
    protected AbstractCacheProcessor(CacheService cacheService) {
        this.cacheService = cacheService;
    }
    
    public Object hit(String key) {
        return cacheService.get(key);
    }
    
    @Override
    public Object hitProcess(String key, String value, Cache cache, Object... extend) {
        
        log.debug("hit cache {}={}", key, value);
        
        Object data;
        if (cache.isList()) {
            data = JsonUtils.jsonToList(value, cache.toClass());
        } else {
            data = JsonUtils.jsonToPojo(value, cache.toClass());
        }
        
        return afterHitProcess(key, data, cache, extend);
    }
    
    protected abstract Object afterHitProcess(String key, Object data, Cache cache, Object... extend);
    
    @Override
    public Object missProcess(String key, Object result, Cache cache) {
        JSONResult jsonResult = (JSONResult) result;
        
        String value = JsonUtils.objectToJson(jsonResult.getData());
        if (ObjectUtils.isEmpty(jsonResult.getData())) {
            // 防止缓存穿透恶意攻击
            cacheService.set(key, value, cache.invalidTimeout());
            log.warn("set invalid cache {}={} timeout={}", key, value, cache.invalidTimeout());
        } else {
            if (cache.timeout() == -1) {
                // 永不过期
                cacheService.set(key, JsonUtils.objectToJson(jsonResult.getData()));
            } else {
                cacheService.set(key, JsonUtils.objectToJson(jsonResult.getData()), cache.timeout());
            }
            log.debug("set cache {}={} timeout={}", key, value, cache.timeout());
        }
        
        return afterMissProcess(jsonResult);
    }
    
    protected abstract Object afterMissProcess(JSONResult jsonResult);
}
