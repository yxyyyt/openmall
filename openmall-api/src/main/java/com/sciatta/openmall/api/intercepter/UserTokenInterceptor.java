package com.sciatta.openmall.api.intercepter;

import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.constants.HeaderConstants;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.service.support.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Created by yangxiaoyu on 2021/9/5<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserTokenInterceptor
 */
@Slf4j
@Component
public class UserTokenInterceptor implements HandlerInterceptor {
    private final CacheService cacheService;
    
    public UserTokenInterceptor(CacheService cacheService) {
        this.cacheService = cacheService;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        String userId = request.getHeader(HeaderConstants.USER_ID);
        String userToken = request.getHeader(HeaderConstants.USER_TOKEN);
        
        if (StringUtils.hasText(userId) && StringUtils.hasText(userToken)) {
            String userTokenFromCache = cacheService.get(getUserTokenKey(userId));
            
            if (!StringUtils.hasText(userTokenFromCache)) {
                returnErrorResponse(response, JSONResult.errorUsingMessage("请登录"));
                return false;
            }
            
            if (!userTokenFromCache.equals(userToken)) {
                returnErrorResponse(response, JSONResult.errorUsingMessage("账号在异地登录"));
                return false;
            }
        } else {
            returnErrorResponse(response, JSONResult.errorUsingMessage("请登录"));
            return false;
        }
        
        return true;
    }
    
    private void returnErrorResponse(HttpServletResponse response, JSONResult jsonResult) {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(jsonResult).getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
    
    private String getUserTokenKey(String userId) {
        return CacheConstants.USER_TOKEN + ":" + userId;
    }
}
