package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.dao.mapper.ext.UserAddressMapper;
import com.sciatta.openmall.dao.pojo.po.mbg.UserAddress;
import com.sciatta.openmall.service.UserAddressService;
import com.sciatta.openmall.service.converter.UserAddressConverter;
import com.sciatta.openmall.service.pojo.dto.UserAddressDTO;
import com.sciatta.openmall.service.pojo.query.UserAddressQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserAddressServiceImpl
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {
    private final UserAddressMapper userAddressMapper;
    
    public UserAddressServiceImpl(UserAddressMapper userAddressMapper) {
        this.userAddressMapper = userAddressMapper;
    }
    
    @Override
    public List<UserAddressDTO> queryByUserId(String userId) {
        List<UserAddress> userAddressList = userAddressMapper.selectByUserId(userId);
        
        return UserAddressConverter.INSTANCE.toUserAddressDTO(userAddressList);
    }
    
    @Override
    public void createUserAddress(UserAddressQuery userAddressQuery) {
        YesOrNo isDefault = YesOrNo.NO;
        // 判断当前用户是否存在地址，如果没有，则新增为默认地址
        List<UserAddressDTO> userAddressDTOList = queryByUserId(userAddressQuery.getUserId());
        if (userAddressDTOList.size() == 0) {
            isDefault = YesOrNo.YES;
        }
        
        UserAddress userAddress = UserAddressConverter.INSTANCE.toUserAddress(userAddressQuery, isDefault);
        
        userAddressMapper.insert(userAddress);
    }
    
    @Override
    public void updateUserAddress(UserAddressQuery userAddressQuery) {
        UserAddress userAddress = UserAddressConverter.INSTANCE.toUserAddress(userAddressQuery);
        
        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }
    
    @Override
    public void deleteUserAddressByUserIdAndAddressId(String userId, String addressId) {
        userAddressMapper.deleteByUserIdAndAddressId(userId, addressId);
    }
    
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        // 更新原默认地址为非默认
        List<UserAddress> userAddressList = userAddressMapper.selectByUserIdAndIsDefault(userId, YesOrNo.YES.type);
        for (UserAddress userAddress : userAddressList) {
            userAddressMapper.updateIsDefaultByUserIdAndAddressId(userAddress.getUserId(), userAddress.getId(), YesOrNo.NO.type);
        }
        
        // 更新为默认地址，只能有一个默认地址
        userAddressMapper.updateIsDefaultByUserIdAndAddressId(userId, addressId, YesOrNo.YES.type);
    }
}
