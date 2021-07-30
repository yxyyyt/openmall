package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.dao.mapper.ext.UserMapper;
import com.sciatta.openmall.dao.pojo.po.mbg.User;
import com.sciatta.openmall.service.UserService;
import com.sciatta.openmall.service.converter.UserConverter;
import com.sciatta.openmall.service.pojo.dto.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    
    @Transactional(readOnly = true)
    @Override
    public UserLoginDTO queryUserForLogin(String username, String password) {
        User user = userMapper.selectByUsernameAndPassword(username, password);
        
        return UserConverter.INSTANCE.userToUserLoginDTO(user);
    }
}
