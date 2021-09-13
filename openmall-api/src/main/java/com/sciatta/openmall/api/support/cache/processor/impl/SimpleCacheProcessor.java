package com.sciatta.openmall.api.support.cache.processor.impl;

import com.sciatta.openmall.api.support.cache.processor.AbstractCacheProcessor;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.service.support.cache.Cache;
import com.sciatta.openmall.service.support.cache.CacheService;
import org.springframework.stereotype.Component;

/**
 * Created by yangxiaoyu on 2021/9/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * SimpleCacheProcess
 */
@Component
public class SimpleCacheProcessor extends AbstractCacheProcessor {
    
    protected SimpleCacheProcessor(CacheService cacheService) {
        super(cacheService);
    }
    
    @Override
    protected Object afterHitProcess(String key, Object data, Cache cache, Object... extend) {
        return JSONResult.success(data);
    }
    
    @Override
    protected Object afterMissProcess(JSONResult jsonResult) {
        return jsonResult;
    }
}
