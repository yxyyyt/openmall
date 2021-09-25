package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.dao.mapper.ext.UserMapper;
import com.sciatta.openmall.dao.pojo.po.mbg.User;
import com.sciatta.openmall.service.UserService;
import com.sciatta.openmall.service.converter.UserConverter;
import com.sciatta.openmall.service.pojo.dto.UserDTO;
import com.sciatta.openmall.service.pojo.query.UserQuery;
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
        
        return UserConverter.INSTANCE.toUserDTO(user);
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
        User user = UserConverter.INSTANCE.toUser(userQuery);
        
        userMapper.insert(user);
        
        return UserConverter.INSTANCE.toUserDTO(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserDTO queryUserByUserId(String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        
        return UserConverter.INSTANCE.toUserDTO(user);
    }
    
    @Override
    public boolean updateUserByUserId(String userId, UserQuery userQuery) {
        
        return userMapper.updateByPrimaryKeySelective(UserConverter.INSTANCE.toUser(userId, userQuery))
                == YesOrNo.YES.type;
    }
}
