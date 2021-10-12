package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.OrderConverter;
import com.sciatta.openmall.api.converter.ShopCartConverter;
import com.sciatta.openmall.api.pojo.query.OrderQuery;
import com.sciatta.openmall.api.pojo.vo.OrderStatusVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.common.constants.CookieConstants;
import com.sciatta.openmall.api.utils.CookieUtils;
import com.sciatta.openmall.api.utils.JsonUtils;
import com.sciatta.openmall.service.OrderService;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.query.ShopCartQuery;
import com.sciatta.openmall.service.support.cache.CacheService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 订单
 */
@Validated
@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;
    private final CacheService cacheService;
    
    public OrderController(OrderService orderService, CacheService cacheService) {
        this.orderService = orderService;
        this.cacheService = cacheService;
    }
    
    @PostMapping("/create")
    public JSONResult create(
            @RequestBody @Validated OrderQuery orderQuery,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        // 从缓存获得购物车
        List<ShopCartQuery> shopCartQueryList = getShopCart(orderQuery);
        List<ShopCartQuery> shopCartPaidList = new ArrayList<>();
        
        // 创建订单
        OrderDTO orderDTO = orderService.createOrder(
                shopCartQueryList,
                shopCartPaidList,
                OrderConverter.INSTANCE.toOrderQuery(orderQuery)
        );
        
        // 创建订单以后，移除购物车中已结算（已提交）的商品
        updateShopCart(orderQuery, shopCartQueryList, shopCartPaidList, request, response);
        
        // 向支付中心发送当前订单，用于保存支付中心的订单数据
        // TODO 支付中心
        
        return JSONResult.success(orderDTO.getOrderId());
    }
    
    @PostMapping("/getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(@RequestParam("orderId") @NotBlank(message = "订单标识不能为空") String orderId) {
        OrderStatusDTO orderStatusDTO = orderService.queryOrderStatusByOrderId(orderId);
        
        OrderStatusVO orderStatusVO = OrderConverter.INSTANCE.toOrderStatusVO(orderStatusDTO);
        
        return JSONResult.success(orderStatusVO);
    }
    
    private List<ShopCartQuery> getShopCart(OrderQuery orderQuery) {
        String shopCart = cacheService.get(getKey(orderQuery));
        
        return ShopCartConverter.INSTANCE.toService(
                JsonUtils.jsonToList(shopCart, com.sciatta.openmall.api.pojo.query.ShopCartQuery.class));
    }
    
    private void updateShopCart(OrderQuery orderQuery,
                                List<ShopCartQuery> shopCartQueryList,
                                List<ShopCartQuery> shopCartPaidList,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        
        shopCartQueryList.removeAll(shopCartPaidList);    // 删除购物车中已支付商品
        
        String result = JsonUtils.objectToJson(ShopCartConverter.INSTANCE.toApi(shopCartQueryList));
        
        // 更新缓存
        CookieUtils.setCookie(request, response, CookieConstants.COOKIE_SHOP_CART, result, true);
        cacheService.set(getKey(orderQuery), result);
    }
    
    private String getKey(com.sciatta.openmall.api.pojo.query.OrderQuery orderQuery) {
        return CacheConstants.SHOP_CART + ":" + orderQuery.getUserId();
    }
}
