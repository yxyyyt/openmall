package com.sciatta.openmall.user.api;

import com.sciatta.openmall.user.pojo.dto.UserAddressDTO;
import com.sciatta.openmall.user.pojo.query.UserAddressQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Rain on 2022/3/22<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * UserAddressService
 */
@FeignClient("openmall-user-service")
@RequestMapping("user-address-api")
public interface UserAddressService {
    @GetMapping("addressList")
    List<UserAddressDTO> queryByUserId(@RequestParam("userId") String userId);

    @GetMapping("addressByUserIdAndAddressId")
    UserAddressDTO queryByUserIdAndAddressId(@RequestParam("userId") String userId,
                                             @RequestParam("addressId") String addressId);

    @PostMapping("address")
    void createUserAddress(@RequestBody UserAddressQuery UserAddressQuery);

    @PutMapping("address")
    void updateUserAddress(@RequestBody UserAddressQuery userAddressQuery);

    @DeleteMapping("address")
    void deleteUserAddressByUserIdAndAddressId(
            @RequestParam("userId") String userId,
            @RequestParam("addressId") String addressId);

    @PutMapping("setDefaultAddress")
    void updateUserAddressToBeDefault(
            @RequestParam("userId") String userId,
            @RequestParam("addressId") String addressId);
}
