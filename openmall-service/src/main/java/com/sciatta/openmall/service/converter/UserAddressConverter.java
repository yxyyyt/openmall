package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.dao.pojo.po.mbg.UserAddress;
import com.sciatta.openmall.service.pojo.dto.UserAddressDTO;
import com.sciatta.openmall.service.pojo.query.UserAddressAddServiceQuery;
import com.sciatta.openmall.service.pojo.query.UserAddressUpdateServiceQuery;
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
    
    public abstract List<UserAddressDTO> userAddressListToUserAddressDTOList(List<UserAddress> userAddressList);
    
    public UserAddress userAddressAddServiceQueryToUserAddress(UserAddressAddServiceQuery userAddressAddServiceQuery, YesOrNo isDefault) {
        
        UserAddress userAddress = new UserAddress();
        userAddress.setId(Sid.nextShort()); // Sid是分布式自增ID，基于snowflake算法
        setUserAddress(userAddress,
                userAddressAddServiceQuery.getUserId(),
                userAddressAddServiceQuery.getReceiver(),
                userAddressAddServiceQuery.getMobile(),
                userAddressAddServiceQuery.getProvince(),
                userAddressAddServiceQuery.getCity(),
                userAddressAddServiceQuery.getDistrict(),
                userAddressAddServiceQuery.getDetail());
        userAddress.setIsDefault(isDefault.type);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        
        return userAddress;
    }
    
    public UserAddress userAddressUpdateServiceQueryToUserAddress(UserAddressUpdateServiceQuery userAddressUpdateServiceQuery) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(userAddressUpdateServiceQuery.getId());
        setUserAddress(userAddress,
                userAddressUpdateServiceQuery.getUserId(),
                userAddressUpdateServiceQuery.getReceiver(),
                userAddressUpdateServiceQuery.getMobile(),
                userAddressUpdateServiceQuery.getProvince(),
                userAddressUpdateServiceQuery.getCity(),
                userAddressUpdateServiceQuery.getDistrict(),
                userAddressUpdateServiceQuery.getDetail());
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
