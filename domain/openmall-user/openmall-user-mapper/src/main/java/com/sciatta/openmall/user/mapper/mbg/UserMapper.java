package com.sciatta.openmall.user.mapper.mbg;

import com.sciatta.openmall.user.pojo.po.mbg.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}