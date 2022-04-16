package com.sciatta.openmall.cart.service.client;

import com.sciatta.openmall.item.api.ItemService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Rain on 2022/4/11<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * ItemServiceFeignClient
 */
@FeignClient("openmall-item-service")
public interface ItemServiceFeignClient extends ItemService {
}
