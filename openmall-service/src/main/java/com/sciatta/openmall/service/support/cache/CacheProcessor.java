package com.sciatta.openmall.service.support.cache;

/**
 * Created by yangxiaoyu on 2021/9/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 解耦缓存业务处理逻辑
 */
public interface CacheProcessor {
    Object hit(String key);
    
    Object hitProcess(String key, String value, Cache cache, Object... extend);
    
    Object missProcess(String key, Object result, Cache cache);
}
