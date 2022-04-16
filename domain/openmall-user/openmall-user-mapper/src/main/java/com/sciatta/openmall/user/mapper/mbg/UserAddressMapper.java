package com.sciatta.openmall.user.mapper.mbg;

import com.sciatta.openmall.user.pojo.po.mbg.UserAddress;

public interface UserAddressMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);
}