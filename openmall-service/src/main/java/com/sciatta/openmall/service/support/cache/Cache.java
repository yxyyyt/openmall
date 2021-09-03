package com.sciatta.openmall.service.support.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yangxiaoyu on 2021/9/3<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 缓存注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    String key();
    
    Class<?> toClass();
    
    boolean isList() default false;
    
    /**
     * 默认过期时间是24小时，单位秒
     *
     * @return 过期时间
     */
    long timeout() default 60 * 60 * 24;
    
    /**
     * 无效数据默认过期时间是5分钟，单位秒
     *
     * @return 过期时间
     */
    long invalidTimeout() default 60 * 5;
}
