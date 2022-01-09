package com.sciatta.openmall.common.utils;

import java.util.UUID;

/**
 * Created by yangxiaoyu on 2021/8/16<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * SidUtils
 */
public class SidUtils {
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
