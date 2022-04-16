package com.sciatta.openmall.common.enums;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * Sex
 */
public enum Sex {
    woman(0, "女"),
    man(1, "男"),
    secret(2, "保密");
    
    public final Integer type;
    public final String value;
    
    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
