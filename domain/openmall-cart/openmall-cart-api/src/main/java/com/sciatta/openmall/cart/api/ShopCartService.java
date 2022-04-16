package com.sciatta.openmall.cart.api;

import com.sciatta.openmall.cart.pojo.query.ShopCartQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Rain on 2022/3/17<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * ShopCartService
 */
@FeignClient("openmall-cart-service")
@RequestMapping("cart-api")
public interface ShopCartService {
    @PostMapping("addItem")
    boolean addItemToShopCart(
            @RequestParam("userId") String userId,
            @RequestBody ShopCartQuery shopCartQuery);

    @PostMapping("removeItem")
    boolean removeItemFromShopCart(
            @RequestParam("userId") String userId,
            @RequestParam("itemSpecId") String itemSpecId);

    @PostMapping("clearCart")
    boolean clearShopCart(@RequestParam("userId") String userId);
}
