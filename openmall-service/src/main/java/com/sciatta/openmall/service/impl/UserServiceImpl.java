package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.dao.mapper.ext.UserMapper;
import com.sciatta.openmall.dao.pojo.po.mbg.User;
import com.sciatta.openmall.service.UserService;
import com.sciatta.openmall.service.converter.UserConverter;
import com.sciatta.openmall.service.pojo.dto.UserDTO;
import com.sciatta.openmall.service.pojo.query.UserQuery;
import com.sciatta.openmall.service.pojo.query.UserServiceQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    @Transactional(readOnly = true)
    @Override
    public UserDTO queryUserForLogin(String username, String password) {
        User user = userMapper.selectByUsernameAndPassword(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        
        return UserConverter.INSTANCE.convert(user);
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean queryUsernameIsExist(String username) {
        User user = userMapper.selectByUsername(username);
        return user != null;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserDTO createUser(UserQuery userQuery) {
        User user = UserConverter.INSTANCE.userRegisterServiceQueryToUser(userQuery);
        
        userMapper.insert(user);
        
        return UserConverter.INSTANCE.convert(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserDTO queryUserByUserId(String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        
        return UserConverter.INSTANCE.convert(user);
    }
    
    @Override
    public boolean updateUserByUserId(String userId, UserServiceQuery userServiceQuery) {
        
        return userMapper.updateByPrimaryKeySelective(UserConverter.INSTANCE.userServiceQueryToUser(userId, userServiceQuery))
                == YesOrNo.YES.type;
    }
}
