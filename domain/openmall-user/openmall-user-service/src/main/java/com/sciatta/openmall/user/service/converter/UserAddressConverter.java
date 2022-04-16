package com.sciatta.openmall.user.service.converter;

import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.user.pojo.dto.UserAddressDTO;
import com.sciatta.openmall.user.pojo.query.UserAddressQuery;
import com.sciatta.openmall.user.pojo.po.mbg.UserAddress;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.n3r.idworker.Sid;

import java.util.Date;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserAddressConverter
 */
@Mapper
public abstract class UserAddressConverter {
    public static UserAddressConverter INSTANCE = Mappers.getMapper(UserAddressConverter.class);

    public abstract UserAddressDTO toUserAddressDTO(UserAddress userAddress);

    public abstract List<UserAddressDTO> toUserAddressDTO(List<UserAddress> userAddressList);

    public UserAddress toUserAddress(UserAddressQuery userAddressQuery, YesOrNo isDefault) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(Sid.nextShort()); // Sid是分布式自增ID，基于snowflake算法
        setUserAddress(userAddress,
                userAddressQuery.getUserId(),
                userAddressQuery.getReceiver(),
                userAddressQuery.getMobile(),
                userAddressQuery.getProvince(),
                userAddressQuery.getCity(),
                userAddressQuery.getDistrict(),
                userAddressQuery.getDetail());
        userAddress.setIsDefault(isDefault.type);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());

        return userAddress;
    }

    public UserAddress toUserAddress(UserAddressQuery userAddressQuery) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(userAddressQuery.getId());
        setUserAddress(userAddress,
                userAddressQuery.getUserId(),
                userAddressQuery.getReceiver(),
                userAddressQuery.getMobile(),
                userAddressQuery.getProvince(),
                userAddressQuery.getCity(),
                userAddressQuery.getDistrict(),
                userAddressQuery.getDetail());
        userAddress.setUpdatedTime(new Date());

        return userAddress;
    }

    private void setUserAddress(UserAddress userAddress,
                                String userId,
                                String receiver,
                                String mobile,
                                String province,
                                String city,
                                String district,
                                String detail) {
        userAddress.setUserId(userId);
        userAddress.setReceiver(receiver);
        userAddress.setMobile(mobile);
        userAddress.setProvince(province);
        userAddress.setCity(city);
        userAddress.setDistrict(district);
        userAddress.setDetail(detail);
    }
}
