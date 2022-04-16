package com.sciatta.openmall.order.service.client;

import com.sciatta.openmall.user.api.UserAddressService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Rain on 2022/4/9<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * UserAddressServiceFeignClient
 */
@FeignClient("openmall-user-service")
public interface UserAddressServiceFeignClient extends UserAddressService {
}
