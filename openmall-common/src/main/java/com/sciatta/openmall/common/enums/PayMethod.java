package com.sciatta.openmall.common.enums;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * PayMethod
 */
public enum PayMethod {
    WEIXIN(1, "微信"),
    ALIPAY(2, "支付宝");
    
    public final Integer type;
    public final String value;
    
    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
