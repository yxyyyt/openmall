package com.sciatta.openmall.user.service.impl;

import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.user.api.UserAddressService;
import com.sciatta.openmall.user.mapper.ext.UserAddressMapper;
import com.sciatta.openmall.user.pojo.dto.UserAddressDTO;
import com.sciatta.openmall.user.pojo.po.mbg.UserAddress;
import com.sciatta.openmall.user.pojo.query.UserAddressQuery;
import com.sciatta.openmall.user.service.converter.UserAddressConverter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserAddressServiceImpl
 */
@RestController
public class UserAddressServiceImpl implements UserAddressService {
    private final UserAddressMapper userAddressMapper;

    public UserAddressServiceImpl(UserAddressMapper userAddressMapper) {
        this.userAddressMapper = userAddressMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAddressDTO> queryByUserId(String userId) {
        List<UserAddress> userAddressList = userAddressMapper.selectByUserId(userId);

        return UserAddressConverter.INSTANCE.toUserAddressDTO(userAddressList);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAddressDTO queryByUserIdAndAddressId(String userId, String addressId) {
        UserAddress userAddress = userAddressMapper.selectByUserIdAndAddressId(userId, addressId);
        return UserAddressConverter.INSTANCE.toUserAddressDTO(userAddress);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
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
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserAddress(UserAddressQuery userAddressQuery) {
        UserAddress userAddress = UserAddressConverter.INSTANCE.toUserAddress(userAddressQuery);

        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserAddressByUserIdAndAddressId(String userId, String addressId) {
        userAddressMapper.deleteByUserIdAndAddressId(userId, addressId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        List<UserAddress> userAddressList = userAddressMapper.selectByUserIdAndIsDefault(userId, YesOrNo.YES.type);

        // 更新原默认地址为非默认，只能有一个默认地址
        if (userAddressList.size() != 0) {
            UserAddress userAddress = userAddressList.get(0);
            if (userAddress.getId().equals(addressId)) {
                return; // 已经是默认地址
            } else {
                userAddressMapper.updateIsDefaultByUserIdAndAddressId(
                        userAddress.getUserId(), userAddress.getId(), YesOrNo.NO.type);
            }
        }

        // 更新为默认地址
        userAddressMapper.updateIsDefaultByUserIdAndAddressId(userId, addressId, YesOrNo.YES.type);
    }
}
