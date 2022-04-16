package com.sciatta.openmall.portlet.service.client;

import com.sciatta.openmall.item.api.ItemCategoryService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Rain on 2022/4/13<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * ItemCategoryServiceFeignClient
 */
@FeignClient("openmall-item-service")
public interface ItemCategoryServiceFeignClient extends ItemCategoryService {
}
