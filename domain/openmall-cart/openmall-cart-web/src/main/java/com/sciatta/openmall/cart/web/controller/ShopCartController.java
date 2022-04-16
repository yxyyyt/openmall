package com.sciatta.openmall.cart.web.controller;

import com.sciatta.openmall.cart.api.ShopCartService;
import com.sciatta.openmall.cart.pojo.query.ShopCartQuery;
import com.sciatta.openmall.cart.service.client.ItemServiceFeignClient;
import com.sciatta.openmall.cart.web.converter.ShopCartConverter;
import com.sciatta.openmall.item.pojo.dto.ItemDTO;
import com.sciatta.openmall.item.pojo.vo.ItemShopCartVO;
import com.sciatta.openmall.pojo.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 购物车，服务端购物车数据持久化到Redis
 */
@Validated
@RestController
@RequestMapping("shopCart")
@Slf4j
public class ShopCartController {
    private final ItemServiceFeignClient itemService;
    private final ShopCartService shopCartService;

    public ShopCartController(ItemServiceFeignClient itemService, ShopCartService shopCartService) {
        this.itemService = itemService;
        this.shopCartService = shopCartService;
    }

    @PostMapping("add")
    public JSONResult add(@RequestParam @NotBlank(message = "用户标识不能为空") String userId, @RequestBody @Validated ShopCartQuery shopCartQuery) {

        log.debug("user {} add {} to shop cart", userId, shopCartQuery);

        boolean test = shopCartService.addItemToShopCart(userId, shopCartQuery);

        if (!test) {
            return JSONResult.errorUsingMessage("用户" + userId + "向购物车添加商品标识" + shopCartQuery.getItemId() + "不合法");
        }

        return JSONResult.success();
    }

    @GetMapping("refresh")
    public JSONResult refresh(@RequestParam @NotBlank(message = "商品规格标识不能为空") String itemSpecIds) {
        List<ItemDTO> itemDTOList = itemService.queryItemsBySpecIds(itemSpecIds);

        List<ItemShopCartVO> itemShopCartVOList = ShopCartConverter.INSTANCE.toItemShopCartVO(itemDTOList);
        return JSONResult.success(itemShopCartVOList);
    }

    @PostMapping("del")
    public JSONResult delete(@RequestParam @NotBlank(message = "用户标识不能为空") String userId, @RequestParam @NotBlank(message = "商品规格不能为空") String itemSpecId) {

        log.debug("user {} del {} from shop cart", userId, itemSpecId);

        boolean test = shopCartService.removeItemFromShopCart(userId, itemSpecId);

        if (!test) {
            return JSONResult.errorUsingMessage("商品规格" + itemSpecId + "不合法");
        }

        return JSONResult.success();
    }
}
