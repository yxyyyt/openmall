package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.ItemConverter;
import com.sciatta.openmall.api.pojo.query.ShopCartQuery;
import com.sciatta.openmall.api.pojo.vo.ItemShopCartVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.service.ItemService;
import com.sciatta.openmall.service.pojo.dto.ItemDTO;
import com.sciatta.openmall.service.support.cache.Cache;
import com.sciatta.openmall.service.support.cache.CacheChildKey;
import com.sciatta.openmall.service.support.cache.CacheExtend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 购物车
 */
@Validated
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
            toClass = ShopCartQuery.class,
            timeout = CacheConstants.NEVER_EXPIRE,
            isList = true,
            processor = "addShopCartCacheProcessor")
    public JSONResult add(
            @RequestParam @CacheChildKey(order = 0) @NotBlank(message = "用户标识不能为空") String userId,
            @RequestBody @CacheExtend @Validated ShopCartQuery shopCartQuery,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        log.debug("user {} add {} to shop cart", userId, shopCartQuery);
        
        List<ShopCartQuery> shopCart = new ArrayList<>();
        shopCart.add(shopCartQuery);
        
        return JSONResult.success(shopCart);
    }
    
    @GetMapping("refresh")
    public JSONResult refresh(
            @RequestParam @NotBlank(message = "商品规格标识不能为空") String itemSpecIds) {
        List<ItemDTO> itemDTOList = itemService.queryShopCartItemsBySpecIds(itemSpecIds);
        
        List<ItemShopCartVO> itemShopCartVOList = ItemConverter.INSTANCE.toItemShopCartVO(itemDTOList);
        return JSONResult.success(itemShopCartVOList);
    }
    
    @PostMapping("del")
    @Cache(key = CacheConstants.SHOP_CART,
            toClass = ShopCartQuery.class,
            timeout = CacheConstants.NEVER_EXPIRE,
            isList = true,
            processor = "deleteShopCartCacheProcessor")
    public JSONResult delete(
            @RequestParam @CacheChildKey(order = 0) @NotBlank(message = "用户标识不能为空") String userId,
            @RequestParam @CacheExtend @NotBlank(message = "商品规格不能为空") String itemSpecId,   // TODO itemSpecId验证不起作用
            HttpServletRequest request,
            HttpServletResponse response) {
        
        log.debug("user {} del {} from shop cart", userId, itemSpecId);
        
        return JSONResult.success();
    }
}
