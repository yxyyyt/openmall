package com.sciatta.openmall.cart.service.impl;

import com.sciatta.openmall.cache.CacheService;
import com.sciatta.openmall.cart.api.ShopCartService;
import com.sciatta.openmall.cart.pojo.query.ShopCartQuery;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.common.utils.JsonUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rain on 2022/3/17<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * ShopCartServiceImpl
 */
@RestController
public class ShopCartServiceImpl implements ShopCartService {
    private final CacheService cacheService;

    public ShopCartServiceImpl(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public boolean addItemToShopCart(String userId, ShopCartQuery shopCartQuery) {
        List<ShopCartQuery> shopCart;
        boolean isHaving = false;

        String cache = cacheService.get(CacheConstants.getShopCartTokenKey(userId));

        if (StringUtils.hasText(cache)) {
            // 缓存中存在
            shopCart = JsonUtils.jsonToList(cache, ShopCartQuery.class);

            // 缓存中若存在相同item，则累加
            assert shopCart != null;
            for (ShopCartQuery test : shopCart) {
                String specId = test.getSpecId();
                if (specId.equals(shopCartQuery.getSpecId())) {
                    test.setBuyCounts(test.getBuyCounts() + shopCartQuery.getBuyCounts());
                    isHaving = true;
                    break;
                }
            }

            if (!isHaving) {
                shopCart.add(shopCartQuery);
            }
        } else {
            shopCart = new ArrayList<>();
            shopCart.add(shopCartQuery);
        }

        // 更新redis中购物车数据
        cacheService.set(CacheConstants.getShopCartTokenKey(userId), JsonUtils.objectToJson(shopCart), CacheConstants.NEVER_EXPIRE);
        return true;
    }

    @Override
    public boolean removeItemFromShopCart(String userId, String itemSpecId) {
        String cache = cacheService.get(CacheConstants.getShopCartTokenKey(userId));

        if (!StringUtils.hasText(cache)) {
            return false;
        }

        List<ShopCartQuery> shopCart = JsonUtils.jsonToList(cache, ShopCartQuery.class);
        ShopCartQuery removeItem = null;

        assert shopCart != null;
        for (ShopCartQuery test : shopCart) {
            String specId = test.getSpecId();
            if (specId.equals(itemSpecId)) {
                removeItem = test;
                break;
            }
        }

        if (removeItem == null) return false;

        shopCart.remove(removeItem);    // 从购物车中删除移除的商品

        // 更新redis中购物车数据
        cacheService.set(CacheConstants.getShopCartTokenKey(userId), JsonUtils.objectToJson(shopCart), CacheConstants.NEVER_EXPIRE);
        return true;
    }

    @Override
    public boolean clearShopCart(String userId) {
        cacheService.del(CacheConstants.getShopCartTokenKey(userId));
        return true;
    }
}
