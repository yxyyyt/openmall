package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * MyOrderService
 */
public interface MyOrderService {
    OrderStatusCountsDTO getOrderStatusCounts(String userId);
}
