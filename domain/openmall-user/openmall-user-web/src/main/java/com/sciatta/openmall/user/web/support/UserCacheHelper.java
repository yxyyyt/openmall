package com.sciatta.openmall.user.web.support;

import com.sciatta.openmall.cache.CacheService;
import com.sciatta.openmall.cart.pojo.query.ShopCartQuery;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.common.constants.CookieConstants;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.common.utils.SidUtils;
import com.sciatta.openmall.user.pojo.vo.UserCookieVO;
import com.sciatta.openmall.web.utils.CookieUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.sciatta.openmall.common.constants.CookieConstants.COOKIE_SHOP_CART;
import static com.sciatta.openmall.common.constants.CookieConstants.COOKIE_USERNAME;

/**
 * Created by yangxiaoyu on 2021/9/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户缓存帮助类，用于管理用户信息和购物车缓存
 */
@Component
public class UserCacheHelper {
    private final CacheService cacheService;

    public UserCacheHelper(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public void sync(UserCookieVO userCookieVO, HttpServletRequest request, HttpServletResponse response) {
        syncUserCache(userCookieVO, request, response);
        syncShopCartCache(userCookieVO, request, response);
    }

    public void clear(String userId, HttpServletRequest request, HttpServletResponse response) {
        clearUserCache(userId, request, response);
        clearShopCartCache(userId, request, response);
    }

    public void syncUserCache(UserCookieVO userCookieVO, HttpServletRequest request, HttpServletResponse response) {
        userCookieVO.setUserUniqueToken(SidUtils.generateUUID());
        cacheService.set(CacheConstants.getUserTokenKey(userCookieVO.getId()), userCookieVO.getUserUniqueToken());
        CookieUtils.setCookie(request, response, COOKIE_USERNAME, JsonUtils.objectToJson(userCookieVO), true);
    }

    public void clearUserCache(String userId, HttpServletRequest request, HttpServletResponse response) {
        cacheService.del(CacheConstants.getUserTokenKey(userId));
        CookieUtils.deleteCookie(request, response, COOKIE_USERNAME);
    }

    public void syncShopCartCache(UserCookieVO userCookieVO, HttpServletRequest request, HttpServletResponse response) {
        String shopCartCookie = CookieUtils.getCookieValue(request, CookieConstants.COOKIE_SHOP_CART, true);
        String shopCartCache = cacheService.get(getShopCartTokenKey(userCookieVO.getId()));

        if (StringUtils.hasText(shopCartCache)) {
            // 缓存不为空
            if (StringUtils.hasText(shopCartCookie)) {
                // cookie不为空，合并缓存和cookie数据，若重复以cookie为准
                List<ShopCartQuery> shopCartCacheList = JsonUtils.jsonToList(shopCartCache, ShopCartQuery.class);
                List<ShopCartQuery> shopCartCookieList = JsonUtils.jsonToList(shopCartCookie, ShopCartQuery.class);
                List<ShopCartQuery> pendingDelete = new ArrayList<>();

                // 缓存和cookie都存在，cookie购买数量覆盖缓存，保留缓存数据
                // 缓存存在，cookie不存在，保留缓存数据
                // 缓存不存在，cookie存在，保留cookie数据
                assert shopCartCacheList != null;
                for (ShopCartQuery redisItem : shopCartCacheList) {
                    assert shopCartCookieList != null;
                    for (ShopCartQuery cookieItem : shopCartCookieList) {
                        if (redisItem.getSpecId().equals(cookieItem.getSpecId())) {
                            redisItem.setBuyCounts(cookieItem.getBuyCounts());
                            pendingDelete.add(cookieItem);
                            break;
                        }
                    }
                }

                // 合并购物车
                assert shopCartCookieList != null;
                shopCartCookieList.removeAll(pendingDelete);    // 移除交集，已经替换缓存数据
                shopCartCacheList.addAll(shopCartCookieList);

                // 更新缓存和cookie
                String result = JsonUtils.objectToJson(shopCartCacheList);
                CookieUtils.setCookie(request, response, CookieConstants.COOKIE_SHOP_CART, result, true);
                cacheService.set(getShopCartTokenKey(userCookieVO.getId()), result, CacheConstants.NEVER_EXPIRE);
            } else {
                // cookie为空，cookie同步缓存数据
                CookieUtils.setCookie(request, response, CookieConstants.COOKIE_SHOP_CART, shopCartCache, true);
            }
        } else {
            // 缓存为空，cookie为空，不做处理
            if (StringUtils.hasText(shopCartCookie)) {
                // cookie不为空，缓存同步cookie数据
                cacheService.set(getShopCartTokenKey(userCookieVO.getId()), shopCartCookie, CacheConstants.NEVER_EXPIRE);
            }
        }

    }

    public void clearShopCartCache(String userId, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, COOKIE_SHOP_CART);
    }

    private String getShopCartTokenKey(String userId) {
        return CacheConstants.SHOP_CART + ":" + userId;
    }
}
