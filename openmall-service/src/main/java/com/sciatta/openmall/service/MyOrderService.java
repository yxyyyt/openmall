package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
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
}
