package com.sciatta.openmall.common.enums;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * YesOrNo
 */
public enum YesOrNo {
    NO(0, "否"),
    YES(1, "是");
    
    public final Integer type;
    public final String value;
    
    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
