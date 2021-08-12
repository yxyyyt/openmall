package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderItemDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.query.OrderCreateServiceQuery;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 订单服务
 */
public interface OrderService {
    OrderDTO createOrder(OrderCreateServiceQuery orderCreateServiceQuery);
    
    OrderStatusDTO queryOrderStatusByOrderId(String orderId);
    
    OrderDTO queryOrderByOrderIdAndUserId(String orderId, String userId);
    
    List<OrderItemDTO> queryOrderItemByOrderId(String orderId);
}
