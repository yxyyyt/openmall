package com.sciatta.openmall.shared.pojo.enums;

/**
 * Created by yangxiaoyu on 2021/8/5<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CommentLevel
 */
public enum CommentLevel {
    GOOD(1, "好评"),
    NORMAL(2, "中评"),
    BAD(3, "差评");
    
    public final Integer type;
    public final String value;
    
    CommentLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
