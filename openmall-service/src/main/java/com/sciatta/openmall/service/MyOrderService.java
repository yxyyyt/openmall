package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusItemDTO;
import com.sciatta.openmall.service.support.PagedContext;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * MyOrderService
 */
public interface MyOrderService {
    OrderStatusCountsDTO queryOrderStatusCounts(String userId);
    
    List<OrderStatusDTO> queryOrdersTrend(String userId, PagedContext pagedContext);
    
    /**
     * 查询订单关联的所有商品，注意此处由于使用分页的原因需要使用级联查询
     *
     * @param userId       用户外键
     * @param orderStatus  订单状态
     * @param pagedContext 分页上下文
     * @return OrderStatusItemDTO集合
     */
    List<OrderStatusItemDTO> queryOrders(String userId, Integer orderStatus, PagedContext pagedContext);
}
