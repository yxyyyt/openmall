package com.sciatta.openmall.item.service.client;

import com.sciatta.openmall.user.api.UserService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by Rain on 2022/4/8<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * UserServiceFeignClient
 */
@FeignClient(value = "openmall-user-service", fallbackFactory = UserServiceFallbackFactory.class)
public interface UserServiceFeignClient extends UserService {
}
