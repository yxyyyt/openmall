package com.sciatta.openmall.api.support.cache;

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
}
