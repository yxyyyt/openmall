package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.OrderConverter;
import com.sciatta.openmall.api.pojo.query.OrderCreateApiQuery;
import com.sciatta.openmall.api.pojo.vo.OrderStatusVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.enums.PayMethod;
import com.sciatta.openmall.service.OrderService;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.query.OrderCreateServiceQuery;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 订单
 */
@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping("/create")
    public JSONResult create(@RequestBody OrderCreateApiQuery orderCreateApiQuery,
                             HttpServletRequest request, HttpServletResponse response) {
        
        if (!orderCreateApiQuery.getPayMethod().equals(PayMethod.WEIXIN.type)
                && !orderCreateApiQuery.getPayMethod().equals(PayMethod.ALIPAY.type)) {
            return JSONResult.errorUsingMessage("支付方式不支持！");
        }
        
        // 创建订单
        OrderCreateServiceQuery orderCreateServiceQuery = OrderConverter.INSTANCE.orderCreateApiQueryToOrderCreateServiceQuery(orderCreateApiQuery);
        OrderDTO orderDTO = orderService.createOrder(orderCreateServiceQuery);
        
        // 创建订单以后，移除购物车中已结算（已提交）的商品
        // TODO 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
        
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
    
}
