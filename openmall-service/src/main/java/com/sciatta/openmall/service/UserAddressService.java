package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.UserAddressDTO;
import com.sciatta.openmall.service.pojo.query.UserAddressAddServiceQuery;
import com.sciatta.openmall.service.pojo.query.UserAddressUpdateServiceQuery;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户地址服务
 */
public interface UserAddressService {
    List<UserAddressDTO> queryByUserId(String userId);
    
    void createUserAddress(UserAddressAddServiceQuery userAddressAddServiceQuery);
    
    void updateUserAddress(UserAddressUpdateServiceQuery userAddressUpdateServiceQuery);
    
    void deleteUserAddressByUserIdAndAddressId(String userId, String addressId);
    
    void updateUserAddressToBeDefault(String userId, String addressId);
}
