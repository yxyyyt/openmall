package com.sciatta.openmall.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CookieUtils
 */
@Slf4j
public class CookieUtils {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);
    
    /**
     * 得到Cookie的值，不编码
     *
     * @param request    httpServletRequest
     * @param cookieName cookieName
     * @return cookieValue
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }
    
    /**
     * 得到Cookie的值
     *
     * @param request    httpServletRequest
     * @param cookieName cookieName
     * @param isDecoder  是否解码
     * @return cookieValue
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (Cookie cookie : cookieList) {
                if (cookie.getName().equals(cookieName)) {
                    if (isDecoder) {
                        retValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } else {
                        retValue = cookie.getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return retValue;
    }
    
    /**
     * 得到Cookie的值
     *
     * @param request      httpServletRequest
     * @param cookieName   cookieName
     * @param encodeString 指定字符编码
     * @return cookieValue
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (Cookie cookie : cookieList) {
                if (cookie.getName().equals(cookieName)) {
                    retValue = URLDecoder.decode(cookie.getValue(), encodeString);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }
    
    /**
     * 设置Cookie的值，不设置生效时间（默认浏览器关闭即失效），也不编码
     *
     * @param request     httpServletRequest
     * @param response    httpServletResponse
     * @param cookieName  cookieName
     * @param cookieValue cookieValue
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue) {
        setCookie(request, response, cookieName, cookieValue, -1);
    }
    
    /**
     * 设置Cookie的值，在指定时间内生效，但不编码
     *
     * @param request      httpServletRequest
     * @param response     httpServletResponse
     * @param cookieName   cookieName
     * @param cookieValue  cookieValue
     * @param cookieMaxAge cookie生效的最大秒数
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxAge) {
        setCookie(request, response, cookieName, cookieValue, cookieMaxAge, false);
    }
    
    /**
     * 设置Cookie的值，不设置生效时间，可设置是否编码
     *
     * @param request     httpServletRequest
     * @param response    httpServletResponse
     * @param cookieName  cookieName
     * @param cookieValue cookieValue
     * @param isEncode    是否编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, boolean isEncode) {
        setCookie(request, response, cookieName, cookieValue, -1, isEncode);
    }
    
    /**
     * 设置Cookie的值，在指定时间内生效，可设置是否编码
     *
     * @param request      httpServletRequest
     * @param response     httpServletResponse
     * @param cookieName   cookieName
     * @param cookieValue  cookieValue
     * @param cookieMaxAge cookie生效的最大秒数
     * @param isEncode     是否编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxAge, boolean isEncode) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxAge, isEncode);
    }
    
    /**
     * 设置Cookie的值，在指定时间内生效，可指定字符编码
     *
     * @param request      httpServletRequest
     * @param response     httpServletResponse
     * @param cookieName   cookieName
     * @param cookieValue  cookieValue
     * @param cookieMaxAge cookie生效的最大秒数
     * @param encodeString 字符编码
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
                                 String cookieValue, int cookieMaxAge, String encodeString) {
        doSetCookie(request, response, cookieName, cookieValue, cookieMaxAge, encodeString);
    }
    
    /**
     * 删除Cookie（带Cookie域名）
     *
     * @param request    httpServletRequest
     * @param response   httpServletResponse
     * @param cookieName cookieName
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,
                                    String cookieName) {
        doSetCookie(request, response, cookieName, null, -1, false);
    }
    
    /**
     * 设置Cookie的值
     *
     * @param request      httpServletRequest
     * @param response     httpServletResponse
     * @param cookieName   cookieName
     * @param cookieValue  cookieValue
     * @param cookieMaxAge cookie生效的最大秒数
     * @param isEncode     是否编码
     */
    private static void doSetCookie(HttpServletRequest request, HttpServletResponse response,
                                    String cookieName, String cookieValue, int cookieMaxAge, boolean isEncode) {
        
        String currentCookieValue;
        
        if (cookieValue == null) {
            currentCookieValue = "";
        } else if (isEncode) {
            try {
                currentCookieValue = URLEncoder.encode(cookieValue, "utf-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
                return;
            }
        } else {
            currentCookieValue = cookieValue;
        }
        
        doSetCookie(request, response, cookieName, currentCookieValue, cookieMaxAge);
    }
    
    /**
     * 设置Cookie的值
     *
     * @param request      httpServletRequest
     * @param response     httpServletResponse
     * @param cookieName   cookieName
     * @param cookieValue  cookieValue
     * @param cookieMaxAge cookie生效的最大秒数
     * @param encodeString 字符编码
     */
    private static void doSetCookie(HttpServletRequest request, HttpServletResponse response,
                                    String cookieName, String cookieValue, int cookieMaxAge, String encodeString) {
        
        String currentCookieValue;
        
        if (cookieValue == null) {
            currentCookieValue = "";
        } else {
            try {
                currentCookieValue = URLEncoder.encode(cookieValue, encodeString);
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
                return;
            }
        }
        
        doSetCookie(request, response, cookieName, currentCookieValue, cookieMaxAge);
    }
    
    /**
     * 设置Cookie的值
     *
     * @param request      httpServletRequest
     * @param response     httpServletResponse
     * @param cookieName   cookieName
     * @param cookieValue  cookieValue
     * @param cookieMaxAge cookie生效的最大秒数
     */
    private static void doSetCookie(HttpServletRequest request, HttpServletResponse response,
                                    String cookieName, String cookieValue, int cookieMaxAge) {
        
        Cookie cookie = new Cookie(cookieName, cookieValue);
        
        if (cookieMaxAge > 0)
            cookie.setMaxAge(cookieMaxAge);
        
        if (request != null) {  // 设置域名的cookie
            String domainName = getDomainName(request);
            log.debug("generate domain name is {}", domainName);
            if (!"localhost".equals(domainName.toLowerCase(Locale.ROOT))) {
                cookie.setDomain(domainName);
            }
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    /**
     * 得到cookie的域名
     *
     * @param request httpServletRequest
     * @return cookie的域名
     */
    private static String getDomainName(HttpServletRequest request) {
        String domainName;
        
        String serverName = request.getRequestURL().toString();
        if (serverName.equals("")) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            
            if (serverName.indexOf(":") > 0) {
                String[] ary = serverName.split(":");
                serverName = ary[0];
            }
            
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            
            // Rfc6265CookieProcessor 校验域名，不能以 . 或 - 开头
            if (len > 3 && !isIp(serverName)) {
                // xxx.yyy.com.cn -> .yyy.com.cn
                // domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
                domainName = domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // xxx.yyy.com or xxx.com -> .yyy.com or .xxx.com
                // domainName = "." + domains[len - 2] + "." + domains[len - 1];
                domainName = domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }
        return domainName;
    }
    
    /**
     * 判断是否是一个IP字符串
     *
     * @param IP 待判断IP字符串
     * @return 是否是一个IP字符串
     */
    private static boolean isIp(String IP) {
        boolean b = false;
        IP = trimSpaces(IP);
        if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = IP.split("\\.");
            if (Integer.parseInt(s[0]) < 255)
                if (Integer.parseInt(s[1]) < 255)
                    if (Integer.parseInt(s[2]) < 255)
                        if (Integer.parseInt(s[3]) < 255)
                            b = true;
        }
        return b;
    }
    
    /**
     * 去掉IP字符串前后所有的空格
     *
     * @param IP 待判断IP字符串
     * @return 格式化后的IP字符串
     */
    public static String trimSpaces(String IP) {
        while (IP.startsWith(" ")) {
            IP = IP.substring(1, IP.length()).trim();
        }
        while (IP.endsWith(" ")) {
            IP = IP.substring(0, IP.length() - 1).trim();
        }
        return IP;
    }
}
