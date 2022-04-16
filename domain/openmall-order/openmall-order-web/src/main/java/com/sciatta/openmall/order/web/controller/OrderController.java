package com.sciatta.openmall.order.web.controller;

import com.sciatta.openmall.cache.CacheService;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.common.constants.CookieConstants;
import com.sciatta.openmall.order.api.OrderService;
import com.sciatta.openmall.order.pojo.dto.OrderDTO;
import com.sciatta.openmall.order.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.order.pojo.query.OrderQuery;
import com.sciatta.openmall.order.pojo.query.OrderStatusVO;
import com.sciatta.openmall.order.web.converter.OrderConverter;
import com.sciatta.openmall.pojo.JSONResult;
import com.sciatta.openmall.web.utils.CookieUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

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

        // 创建订单
        OrderDTO orderDTO = orderService.createOrder(orderQuery);

        // 更新购物车缓存
        updateShopCart(orderQuery, orderDTO, request, response);

        // 向支付中心发送当前订单，用于保存支付中心的订单数据 MerchantOrderDTO
        // TODO 支付中心

        return JSONResult.success(orderDTO.getOrderId());
    }

    @PostMapping("/getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(@RequestParam("orderId") @NotBlank(message = "订单标识不能为空") String orderId) {
        OrderStatusDTO orderStatusDTO = orderService.queryOrderStatusByOrderId(orderId);

        OrderStatusVO orderStatusVO = OrderConverter.INSTANCE.toOrderStatusVO(orderStatusDTO);

        return JSONResult.success(orderStatusVO);
    }

    // private

    private void updateShopCart(OrderQuery orderQuery,
                                OrderDTO orderDTO,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        CookieUtils.setCookie(request, response, CookieConstants.COOKIE_SHOP_CART,
                orderDTO.getUnPaidShopCart(), true);
        cacheService.set(CacheConstants.getShopCartTokenKey(orderQuery.getUserId()), orderDTO.getUnPaidShopCart());
    }
}
