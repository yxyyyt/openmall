package com.sciatta.openmall.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * MobileEmailUtils
 */
public class ValidCheckUtils {
    public static boolean checkMobileIsValid(String mobile) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
    
    public static boolean checkEmailIsValid(String email) {
        return email.matches("[\\w.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+");
    }
}
