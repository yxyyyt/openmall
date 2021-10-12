package com.sciatta.openmall.api.support.cache;

import com.sciatta.openmall.api.pojo.vo.UserCookieVO;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.api.utils.CookieUtils;
import com.sciatta.openmall.api.utils.JsonUtils;
import com.sciatta.openmall.common.utils.SidUtils;
import com.sciatta.openmall.service.support.cache.CacheService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.sciatta.openmall.common.constants.CookieConstants.COOKIE_SHOP_CART;
import static com.sciatta.openmall.common.constants.CookieConstants.COOKIE_USERNAME;

/**
 * Created by yangxiaoyu on 2021/9/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户缓存帮助类，用于管理用户缓存，包括客户端和服务端缓存
 */
@Component
public class UserCacheHelper {
    private final CacheService cacheService;
    
    public UserCacheHelper(CacheService cacheService) {
        this.cacheService = cacheService;
    }
    
    /**
     * 设置用户缓存，包括客户端和服务端缓存
     *
     * @param userCookieVO userCookieVO
     * @param request      request
     * @param response     response
     */
    public void setUserCache(UserCookieVO userCookieVO, HttpServletRequest request, HttpServletResponse response) {
        String userTokenValue = getUserTokenValue();
        
        userCookieVO.setUserUniqueToken(userTokenValue);
        
        // 设置服务端缓存
        cacheService.set(getUserTokenKey(userCookieVO.getId()), userTokenValue);
        
        // 设置客户端缓存
        CookieUtils.setCookie(request, response, COOKIE_USERNAME, JsonUtils.objectToJson(userCookieVO), true);
    }
    
    public void clearUserCache(String userId, HttpServletRequest request, HttpServletResponse response) {
        // 清理服务端缓存
        cacheService.del(getUserTokenKey(userId));
        
        // 清理客户端缓存
        CookieUtils.deleteCookie(request, response, COOKIE_USERNAME);
        CookieUtils.deleteCookie(request, response, COOKIE_SHOP_CART);
    }
    
    private String getUserTokenKey(String userId) {
        return CacheConstants.USER_TOKEN + ":" + userId;
    }
    
    private String getUserTokenValue() {
        return SidUtils.generateUUID();
    }
}
