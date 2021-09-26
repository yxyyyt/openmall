package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.*;
import com.sciatta.openmall.service.pojo.query.OrderQuery;
import com.sciatta.openmall.service.pojo.query.ShopCartQuery;
import com.sciatta.openmall.service.support.paged.PagedContext;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 订单服务
 */
public interface OrderService {
    OrderDTO createOrder(List<ShopCartQuery> shopCartQueryList,
                         List<ShopCartQuery> shopCartPaidList,
                         OrderQuery orderQuery);
    
    OrderStatusDTO queryOrderStatusByOrderId(String orderId);
    
    OrderDTO queryOrderByOrderIdAndUserId(String orderId, String userId);
    
    List<OrderItemDTO> queryOrderItemByOrderId(String orderId);
    
    OrderStatusCountsDTO queryOrderStatusCounts(String userId);
    
    List<OrderStatusDTO> queryOrdersTrend(String userId, PagedContext pagedContext);
    
    /**
     * 查询订单关联的所有商品，注意此处由于使用分页的原因需要使用级联即时查询
     *
     * @param userId       用户外键
     * @param orderStatus  订单状态
     * @param pagedContext 分页上下文
     * @return OrderStatusItemDTO集合
     */
    List<OrderStatusDTO> queryOrders(String userId, Integer orderStatus, PagedContext pagedContext);
    
    boolean updateReceiveOrderStatus(String orderId);
    
    boolean deleteOrderByOrderIdAndUserId(String orderId, String userId);
}
