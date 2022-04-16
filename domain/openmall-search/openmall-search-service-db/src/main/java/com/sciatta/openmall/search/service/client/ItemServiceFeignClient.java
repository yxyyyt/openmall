package com.sciatta.openmall.search.service.client;

import com.sciatta.openmall.item.api.ItemService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Rain on 2022/4/15<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * ItemServiceFeignClient
 */
@FeignClient("openmall-item-service")
public interface ItemServiceFeignClient extends ItemService {
}
