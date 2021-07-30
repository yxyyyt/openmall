package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.UserLoginDTO;

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
     * @return UserDTO
     */
    UserLoginDTO queryUserForLogin(String username, String password);
}
