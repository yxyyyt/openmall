package com.sciatta.openmall.common.utils;

import org.junit.Test;

/**
 * Created by yangxiaoyu on 2021/8/16<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * SidUtilsTests
 */
public class SidUtilsTests {
    @Test
    public void testGenerateUUID() {
        for (int i = 0; i < 5; i++) {
            String uuid = SidUtils.generateUUID();
            System.out.println(uuid);
        }
    }
}
