package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.ItemConverter;
import com.sciatta.openmall.api.pojo.query.ShopCartAddApiQuery;
import com.sciatta.openmall.api.pojo.vo.ShopCartItemVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.service.ItemService;
import com.sciatta.openmall.service.pojo.dto.ShopCartItemDTO;
import com.sciatta.openmall.service.support.cache.Cache;
import com.sciatta.openmall.service.support.cache.CacheChildKey;
import com.sciatta.openmall.service.support.cache.CacheExtend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 购物车
 */
@RestController
@RequestMapping("shopCart")
@Slf4j
public class ShopCartController {
    private final ItemService itemService;
    
    public ShopCartController(ItemService itemService) {
        this.itemService = itemService;
    }
    
    @PostMapping("add")
    @Cache(key = CacheConstants.SHOP_CART,
            toClass = ShopCartAddApiQuery.class,
            timeout = CacheConstants.NEVER_EXPIRE,
            isList = true,
            processor = "addShopCartCacheProcessor")
    public JSONResult add(@RequestParam @CacheChildKey(order = 0) String userId,
                          @RequestBody @CacheExtend ShopCartAddApiQuery shopCartAddApiQuery,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        
        log.debug("user {} add {} to shop cart", userId, shopCartAddApiQuery);
        
        if (!StringUtils.hasText(userId)) {
            return JSONResult.errorUsingMessage("用户不能为空");
        }
        
        List<ShopCartAddApiQuery> shopCart = new ArrayList<>();
        shopCart.add(shopCartAddApiQuery);
        
        return JSONResult.success(shopCart);
    }
    
    @GetMapping("refresh")
    public JSONResult refresh(@RequestParam String itemSpecIds) {
        List<ShopCartItemDTO> shopCartItemDTOList = itemService.queryShopCartItemsBySpecIds(itemSpecIds);
        
        List<ShopCartItemVO> shopCartItemVOList = ItemConverter.INSTANCE.shopCartItemDTOListToShopCartItemVOList(shopCartItemDTOList);
        return JSONResult.success(shopCartItemVOList);
    }
    
    @PostMapping("del")
    @Cache(key = CacheConstants.SHOP_CART,
            toClass = ShopCartAddApiQuery.class,
            timeout = CacheConstants.NEVER_EXPIRE,
            isList = true,
            processor = "deleteShopCartCacheProcessor")
    public JSONResult delete(@RequestParam @CacheChildKey(order = 0) String userId,
                             @RequestParam @CacheExtend String itemSpecId,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        
        log.debug("user {} del {} from shop cart", userId, itemSpecId);
        
        if (!StringUtils.hasText(userId) || !StringUtils.hasText(itemSpecId)) {
            return JSONResult.errorUsingMessage("用户或商品规格不能为空");
        }
        
        return JSONResult.success();
    }
}
