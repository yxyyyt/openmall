package com.sciatta.openmall.cache;

/**
 * Created by yangxiaoyu on 2021/9/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 解耦缓存业务处理逻辑
 */
public interface CacheProcessor {
    String tryHit(String key);
    
    Object hit(String key, String value, Cache cache, Object... extend);
    
    Object afterReturnFromSource(String key, Object result, Cache cache, Object... extend);
}
