package com.sciatta.openmall.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yangxiaoyu on 2021/9/3<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CacheExtend
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CacheExtend {
}
