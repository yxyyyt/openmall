package com.sciatta.openmall.api.support.cache.processor.impl;

import com.sciatta.openmall.api.pojo.query.ShopCartQuery;
import com.sciatta.openmall.api.support.cache.processor.AbstractCacheProcessor;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.constants.CookieConstants;
import com.sciatta.openmall.api.utils.CookieUtils;
import com.sciatta.openmall.api.utils.JsonUtils;
import com.sciatta.openmall.service.support.cache.Cache;
import com.sciatta.openmall.service.support.cache.CacheService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/9/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * LoginShopCartCacheProcessor
 */
@Component
public class LoginShopCartCacheProcessor extends AbstractCacheProcessor {
    protected LoginShopCartCacheProcessor(CacheService cacheService) {
        super(cacheService);
    }
    
    @Override
    protected Object afterHitProcess(String key, Object data, Cache cache, Object... extend) {
        HttpServletRequest request = (HttpServletRequest) extend[0];
        HttpServletResponse response = (HttpServletResponse) extend[1];
        
        // 从cookie中获取购物车
        String shopCartCookie = CookieUtils.getCookieValue(request, CookieConstants.COOKIE_SHOP_CART, true);
        
        // redis不为空，cookie不为空，合并redis和cookie数据
        // redis不为空，cookie为空，cookie同步redis数据
        if (StringUtils.hasText(shopCartCookie)) {
            List<ShopCartQuery> shopCartFromRedis = (List<ShopCartQuery>) data;
            List<ShopCartQuery> shopCartFromCookie = JsonUtils.jsonToList(shopCartCookie, ShopCartQuery.class);
            List<ShopCartQuery> pendingDelete = new ArrayList<>();
            
            // redis和cookie都存在，cookie购买数量覆盖redis，以redis为准
            // redis存在，cookie不存在，以redis为准
            // redis不存在，cookie存在，以cookie为准
            for (ShopCartQuery redisItem : shopCartFromRedis) {
                for (ShopCartQuery cookieItem : shopCartFromCookie) {
                    if (redisItem.getSpecId().equals(cookieItem.getSpecId())) {
                        redisItem.setBuyCounts(cookieItem.getBuyCounts());
                        pendingDelete.add(cookieItem);
                        break;
                    }
                }
            }
            
            // 合并购物车
            shopCartFromCookie.removeAll(pendingDelete);
            shopCartFromRedis.addAll(shopCartFromCookie);
            
            // 更新redis和cookie
            String result = JsonUtils.objectToJson(shopCartFromRedis);
            CookieUtils.setCookie(request, response, CookieConstants.COOKIE_SHOP_CART, result, true);
            cacheService.set(key, result, cache.timeout());
        } else {
            CookieUtils.setCookie(request, response, CookieConstants.COOKIE_SHOP_CART, JsonUtils.objectToJson(data), true);
        }
        
        return JSONResult.success();
    }
    
    @Override
    public Object missProcess(String key, Object result, Cache cache, Object... extend) {
        HttpServletRequest request = (HttpServletRequest) extend[0];
        HttpServletResponse response = (HttpServletResponse) extend[1];
        
        // 从cookie中获取购物车
        String shopCartCookie = CookieUtils.getCookieValue(request, CookieConstants.COOKIE_SHOP_CART, true);
        
        // redis为空，cookie为空，不做处理
        // redis为空，cookie不为空，redis同步cookie数据
        if (StringUtils.hasText(shopCartCookie)) {
            cacheService.set(key, shopCartCookie, cache.timeout());
        }
        
        return afterMissProcess(JSONResult.success());
    }
    
    @Override
    protected Object afterMissProcess(JSONResult jsonResult) {
        return jsonResult;
    }
}
