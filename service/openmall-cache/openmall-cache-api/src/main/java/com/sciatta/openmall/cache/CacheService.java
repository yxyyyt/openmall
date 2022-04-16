package com.sciatta.openmall.cache;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangxiaoyu on 2021/9/3<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CacheService
 */
public interface CacheService {
    
    /**
     * 增加缓存，同时设置过期时间，单位秒；当过期时间是-1时，永不过期
     *
     * @param key     key
     * @param value   value
     * @param timeout timeout
     */
    void set(String key, String value, long timeout);
    
    /**
     * 增加缓存，同时设置过期时间；当过期时间是-1时，永不过期
     *
     * @param key      key
     * @param value    value
     * @param timeout  timeout
     * @param timeUnit timeUnit
     */
    void set(String key, String value, long timeout, TimeUnit timeUnit);
    
    /**
     * 增加缓存；过期时间永不过期
     *
     * @param key   key
     * @param value value
     */
    void set(String key, String value);
    
    /**
     * 删除缓存
     *
     * @param key key
     */
    void del(String key);
    
    /**
     * 获取缓存
     *
     * @param key key
     * @return 缓存内容
     */
    String get(String key);
    
    /**
     * 批量获取缓存
     *
     * @param keys keys
     * @return 缓存内容集合
     */
    List<String> multiGet(List<String> keys);
    
    /**
     * 设置过期时间，单位秒
     *
     * @param key     key
     * @param timeout timeout
     */
    void expire(String key, long timeout);
    
    /**
     * 设置过期时间
     *
     * @param key      key
     * @param timeout  timeout
     * @param timeUnit timeUnit
     */
    void expire(String key, long timeout, TimeUnit timeUnit);
    
    /**
     * 设置过期日期
     *
     * @param key  key
     * @param date date
     */
    void expire(String key, Date date);
    
    /**
     * 获取剩余过期时间，单位秒
     *
     * @param key key
     * @return 剩余过期时间
     */
    Long getExpire(String key);
    
    /**
     * 获取剩余过期时间
     *
     * @param key      key
     * @param timeUnit timeUnit
     * @return 剩余过期时间
     */
    Long getExpire(String key, TimeUnit timeUnit);
}
