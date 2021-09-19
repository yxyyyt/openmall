package com.sciatta.openmall.api.support.cache.processor.impl;

import com.sciatta.openmall.api.pojo.query.ItemShopCartQuery;
import com.sciatta.openmall.api.support.cache.processor.AbstractCacheProcessor;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.service.support.cache.Cache;
import com.sciatta.openmall.service.support.cache.CacheService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/9/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * AddShopCartCacheProcessor
 */
@Component
public class AddShopCartCacheProcessor extends AbstractCacheProcessor {
    protected AddShopCartCacheProcessor(CacheService cacheService) {
        super(cacheService);
    }
    
    @Override
    protected Object afterHitProcess(String key, Object data, Cache cache, Object... extend) {
        boolean isHaving = false;
        List<ItemShopCartQuery> shopCart = (List<ItemShopCartQuery>) data;
        ItemShopCartQuery newItem = (ItemShopCartQuery) extend[0];
        
        for (ItemShopCartQuery test : shopCart) {
            String specId = test.getSpecId();
            if (specId.equals(newItem.getSpecId())) {
                test.setBuyCounts(test.getBuyCounts() + newItem.getBuyCounts());
                isHaving = true;
            }
        }
        if (!isHaving) {
            shopCart.add(newItem);
        }
        
        // 更新redis中购物车数据
        cacheService.set(key, JsonUtils.objectToJson(shopCart), cache.timeout());
        
        return JSONResult.success();
    }
    
    @Override
    protected Object afterMissProcess(JSONResult jsonResult) {
        return JSONResult.success();
    }
}
