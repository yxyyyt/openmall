package com.sciatta.openmall.item.service.client;

import com.sciatta.openmall.order.api.OrderService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Rain on 2022/4/12<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * OrderServiceFeignClient
 */
@FeignClient("openmall-order-service")
public interface OrderServiceFeignClient extends OrderService {
}
