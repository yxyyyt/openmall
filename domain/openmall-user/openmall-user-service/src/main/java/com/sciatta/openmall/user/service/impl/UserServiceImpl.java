package com.sciatta.openmall.user.service.impl;

import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.user.api.UserService;
import com.sciatta.openmall.user.mapper.ext.UserMapper;
import com.sciatta.openmall.user.pojo.dto.UserDTO;
import com.sciatta.openmall.user.pojo.po.mbg.User;
import com.sciatta.openmall.user.pojo.query.UserQuery;
import com.sciatta.openmall.user.service.converter.UserConverter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserServiceImpl
 */
@RestController
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO queryUserForLogin(String username, String password) {
        User user = userMapper.selectByUsernameAndPassword(username, DigestUtils.md5DigestAsHex(password.getBytes()));

        return UserConverter.INSTANCE.toUserDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean queryUsernameIsExist(String username) {
        User user = userMapper.selectByUsername(username);
        return user != null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
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
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateUserByUserId(String userId, UserQuery userQuery) {
        return userMapper.updateByPrimaryKeySelective(UserConverter.INSTANCE.toUser(userId, userQuery)) == YesOrNo.YES.type;
    }
}
