package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.OrderConverter;
import com.sciatta.openmall.api.converter.ShopCartConverter;
import com.sciatta.openmall.api.pojo.query.OrderCreateApiQuery;
import com.sciatta.openmall.api.pojo.query.ShopCartAddApiQuery;
import com.sciatta.openmall.api.pojo.vo.OrderStatusVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.common.constants.CookieConstants;
import com.sciatta.openmall.common.enums.PayMethod;
import com.sciatta.openmall.common.utils.CookieUtils;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.service.OrderService;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.query.OrderCreateServiceQuery;
import com.sciatta.openmall.service.pojo.query.ShopCartAddServiceQuery;
import com.sciatta.openmall.service.support.cache.CacheService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 订单
 */
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
    public JSONResult create(@RequestBody OrderCreateApiQuery orderCreateApiQuery,
                             HttpServletRequest request, HttpServletResponse response) {
        
        if (!orderCreateApiQuery.getPayMethod().equals(PayMethod.WEIXIN.type)
                && !orderCreateApiQuery.getPayMethod().equals(PayMethod.ALIPAY.type)) {
            return JSONResult.errorUsingMessage("支付方式不支持！");
        }
        
        // 从缓存获得购物车
        List<ShopCartAddServiceQuery> shopCartAddServiceQueryList = getShopCart(orderCreateApiQuery);
        List<ShopCartAddServiceQuery> paidShopCartList = new ArrayList<>();
        
        // 创建订单
        OrderCreateServiceQuery orderCreateServiceQuery = OrderConverter.INSTANCE.orderCreateApiQueryToOrderCreateServiceQuery(orderCreateApiQuery);
        OrderDTO orderDTO = orderService.createOrder(shopCartAddServiceQueryList, paidShopCartList, orderCreateServiceQuery);
        
        // 创建订单以后，移除购物车中已结算（已提交）的商品
        setShopCart(orderCreateApiQuery, shopCartAddServiceQueryList, paidShopCartList, request, response);
        
        // 向支付中心发送当前订单，用于保存支付中心的订单数据
        // TODO 支付中心
        
        return JSONResult.success(orderDTO.getOrderId());
    }
    
    @PostMapping("/getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(@RequestParam("orderId") String orderId) {
        OrderStatusDTO orderStatusDTO = orderService.queryOrderStatusByOrderId(orderId);
        
        OrderStatusVO orderStatusVO = OrderConverter.INSTANCE.orderStatusDTOToOrderStatusVO(orderStatusDTO);
        
        return JSONResult.success(orderStatusVO);
    }
    
    private List<ShopCartAddServiceQuery> getShopCart(OrderCreateApiQuery orderCreateApiQuery) {
        String shopCart = cacheService.get(getKey(orderCreateApiQuery));
        List<ShopCartAddApiQuery> shopCartAddApiQueryList = JsonUtils.jsonToList(shopCart, ShopCartAddApiQuery.class);
        
        return ShopCartConverter.INSTANCE.shopCartAddApiQueryListToShopCartAddServiceQueryList(shopCartAddApiQueryList);
    }
    
    private void setShopCart(OrderCreateApiQuery orderCreateApiQuery,
                             List<ShopCartAddServiceQuery> shopCartAddServiceQueryList,
                             List<ShopCartAddServiceQuery> paidShopCartList,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        shopCartAddServiceQueryList.removeAll(paidShopCartList);    // 删除购物车中已支付商品
        
        List<ShopCartAddApiQuery> shopCartAddApiQueryList =
                ShopCartConverter.INSTANCE.shopCartAddServiceQueryListToShopCartAddApiQueryList(shopCartAddServiceQueryList);
        
        String result = JsonUtils.objectToJson(shopCartAddApiQueryList);
        
        // 更新缓存
        CookieUtils.setCookie(request, response, CookieConstants.COOKIE_SHOP_CART, result, true);
        cacheService.set(getKey(orderCreateApiQuery), result);
    }
    
    private String getKey(OrderCreateApiQuery orderCreateApiQuery) {
        return CacheConstants.SHOP_CART + ":" + orderCreateApiQuery.getUserId();
    }
}
