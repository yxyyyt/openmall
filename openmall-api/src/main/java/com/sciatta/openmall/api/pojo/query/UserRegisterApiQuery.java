package com.sciatta.openmall.api.pojo.query;

import lombok.Data;

/**
 * Created by yangxiaoyu on 2021/7/30<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户注册请求
 */
@Data
public class UserRegisterApiQuery {
    private String username;
    private String password;
    private String confirmPassword;
}
