package com.sciatta.openmall.cache.redis;

import com.sciatta.openmall.cache.Cache;
import com.sciatta.openmall.cache.CacheProcessor;
import com.sciatta.openmall.cache.CacheService;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.pojo.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * Created by Rain on 2022/4/13<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * RedisCacheProcessor
 */
@Slf4j
public class RedisCacheProcessor implements CacheProcessor {
    private CacheService cacheService;

    public RedisCacheProcessor(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public String tryHit(String key) {
        return cacheService.get(key);
    }

    @Override
    public Object hit(String key, String value, Cache cache, Object... extend) {
        log.debug("hit cache {}={}", key, value);

        Object data;
        if (cache.isList()) {
            data = JsonUtils.jsonToList(value, cache.toClass());
        } else {
            data = JsonUtils.jsonToPojo(value, cache.toClass());
        }
        return JSONResult.success(data);
    }

    @Override
    public Object afterReturnFromSource(String key, Object result, Cache cache, Object... extend) {
        JSONResult jsonResult = (JSONResult) result;
        Assert.notNull(jsonResult, "JSONResult is null");

        String value = JsonUtils.objectToJson(jsonResult.getData());
        if (ObjectUtils.isEmpty(jsonResult.getData())) {
            // 防止缓存穿透恶意攻击
            cacheService.set(key, value, cache.invalidTimeout());
            log.warn("set invalid cache {}={} timeout={}", key, value, cache.invalidTimeout());
        } else {
            cacheService.set(key, value, cache.timeout());
            log.debug("set cache {}={} timeout={}", key, value, cache.timeout());
        }
        return jsonResult;
    }
}
