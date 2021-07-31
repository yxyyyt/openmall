package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.dao.mapper.ext.UserMapper;
import com.sciatta.openmall.dao.pojo.po.mbg.User;
import com.sciatta.openmall.service.UserService;
import com.sciatta.openmall.service.converter.UserLoginConverter;
import com.sciatta.openmall.service.converter.UserRegisterConverter;
import com.sciatta.openmall.service.pojo.dto.UserLoginDTO;
import com.sciatta.openmall.service.pojo.dto.UserRegisterDTO;
import com.sciatta.openmall.service.pojo.query.UserRegisterServiceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UserMapper userMapper;
    
    @Transactional(readOnly = true)
    @Override
    public UserLoginDTO queryUserForLogin(String username, String password) {
        User user = userMapper.selectByUsernameAndPassword(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        
        return UserLoginConverter.INSTANCE.userToUserLoginDTO(user);
    }
    
    @Transactional(readOnly = true)
    @Override
    public boolean queryUsernameIsExist(String username) {
        User user = userMapper.selectByUsername(username);
        return user != null;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserRegisterDTO createUser(UserRegisterServiceQuery userRegisterServiceQuery) {
        User user = UserRegisterConverter.INSTANCE.userRegisterServiceQueryToUser(userRegisterServiceQuery);
        
        int id = userMapper.insert(user);
        logger.debug(id + " create");
        
        return UserRegisterConverter.INSTANCE.userToUserRegisterDTO(user);
    }
}
