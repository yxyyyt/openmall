package com.sciatta.openmall.api.support.cache;

import com.sciatta.openmall.api.pojo.query.ItemShopCartQuery;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.service.support.cache.Cache;
import com.sciatta.openmall.service.support.cache.CacheChildKey;
import com.sciatta.openmall.service.support.cache.CacheExtend;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yangxiaoyu on 2021/9/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * LoginShopCartCacheHelper
 */
@Component
public class LoginShopCartCacheHelper {
    @Cache(key = CacheConstants.SHOP_CART,
            toClass = ItemShopCartQuery.class,
            timeout = CacheConstants.NEVER_EXPIRE,
            isList = true,
            processor = "loginShopCartCacheProcessor")
    public JSONResult processCache(
            @CacheChildKey(order = 0) String userId,
            @CacheExtend HttpServletRequest request,
            @CacheExtend HttpServletResponse response) {
        return JSONResult.success();
    }
}
