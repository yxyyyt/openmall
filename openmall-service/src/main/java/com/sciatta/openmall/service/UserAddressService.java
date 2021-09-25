package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.UserAddressDTO;
import com.sciatta.openmall.service.pojo.query.UserAddressQuery;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户地址服务
 */
public interface UserAddressService {
    List<UserAddressDTO> queryByUserId(String userId);
    
    void createUserAddress(UserAddressQuery UserAddressQuery);
    
    void updateUserAddress(UserAddressQuery userAddressQuery);
    
    void deleteUserAddressByUserIdAndAddressId(String userId, String addressId);
    
    void updateUserAddressToBeDefault(String userId, String addressId);
}
