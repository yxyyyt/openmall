package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.UserDTO;
import com.sciatta.openmall.service.pojo.dto.UserLoginDTO;
import com.sciatta.openmall.service.pojo.dto.UserRegisterDTO;
import com.sciatta.openmall.service.pojo.query.UserRegisterServiceQuery;
import com.sciatta.openmall.service.pojo.query.UserServiceQuery;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户服务
 */
public interface UserService {
    /**
     * 登录查询用户是否存在
     *
     * @param username 用户名
     * @param password 用户密码
     * @return UserLoginDTO
     */
    UserLoginDTO queryUserForLogin(String username, String password);
    
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
     * @param userRegisterServiceQuery userRegisterServiceQuery
     * @return UserRegisterDTO
     */
    UserRegisterDTO createUser(UserRegisterServiceQuery userRegisterServiceQuery);
    
    UserDTO queryUserByUserId(String userId);
    
    boolean updateUserByUserId(String userId, UserServiceQuery userServiceQuery);
}
