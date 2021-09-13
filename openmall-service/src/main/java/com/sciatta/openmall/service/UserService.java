package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.UserDTO;
import com.sciatta.openmall.service.pojo.query.UserQuery;
import com.sciatta.openmall.service.pojo.query.UserServiceQuery;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户服务
 */
public interface UserService {
    /**
     * 查询用户是否存在
     *
     * @param username 用户名
     * @param password 用户密码
     * @return UserDTO
     */
    UserDTO queryUserForLogin(String username, String password);
    
    /**
     * 查询用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean queryUsernameIsExist(String username);
    
    /**
     * 创建用户
     *
     * @param userQuery userQuery
     * @return UserRegisterDTO
     */
    UserDTO createUser(UserQuery userQuery);
    
    UserDTO queryUserByUserId(String userId);
    
    boolean updateUserByUserId(String userId, UserServiceQuery userServiceQuery);
}
