package com.sciatta.openmall.common.constants;

/**
 * Created by yangxiaoyu on 2021/9/3<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 缓存常量
 */
public class CacheConstants {
    public static final long NEVER_EXPIRE = -1;

    public static final String CAROUSELS = "carousels";

    public static final String CATEGORIES = "categories";

    public static final String SUB_CATEGORIES = "subCategories";

    public static final String SHOP_CART = "shopCart";

    public static final String USER_TOKEN = "userToken";

    public static String getUserTokenKey(String userId) {
        return USER_TOKEN + ":" + userId;
    }

    public static String getShopCartTokenKey(String userId) {
        return SHOP_CART + ":" + userId;
    }
}
