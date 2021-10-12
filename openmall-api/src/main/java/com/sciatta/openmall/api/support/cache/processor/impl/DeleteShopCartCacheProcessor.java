package com.sciatta.openmall.api.support.cache.processor.impl;

import com.sciatta.openmall.api.pojo.query.ShopCartQuery;
import com.sciatta.openmall.api.support.cache.processor.AbstractCacheProcessor;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.api.utils.JsonUtils;
import com.sciatta.openmall.service.support.cache.Cache;
import com.sciatta.openmall.service.support.cache.CacheService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/9/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * DeleteShopCartCacheProcessor
 */
@Component
public class DeleteShopCartCacheProcessor extends AbstractCacheProcessor {
    protected DeleteShopCartCacheProcessor(CacheService cacheService) {
        super(cacheService);
    }
    
    @Override
    protected Object afterHitProcess(String key, Object data, Cache cache, Object... extend) {
        List<ShopCartQuery> shopCart = (List<ShopCartQuery>) data;
        String itemSpecId = (String) extend[0];
        ShopCartQuery removeItem = null;
        
        for (ShopCartQuery test : shopCart) {
            String specId = test.getSpecId();
            if (specId.equals(itemSpecId)) {
                removeItem = test;
                break;
            }
        }
        
        shopCart.remove(removeItem);    // 删除移除的商品
        
        // 更新redis中购物车数据
        cacheService.set(key, JsonUtils.objectToJson(shopCart), cache.timeout());
        
        return JSONResult.success();
    }
    
    @Override
    public Object missProcess(String key, Object result, Cache cache, Object... extend) {
        return afterMissProcess(JSONResult.success());
    }
    
    @Override
    protected Object afterMissProcess(JSONResult jsonResult) {
        return jsonResult;
    }
}
