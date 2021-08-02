package com.sciatta.openmall.api.pojo.query;

import lombok.Data;

/**
 * Created by yangxiaoyu on 2021/7/16<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户登录请求
 */
@Data
public class UserLoginApiQuery {
    private String username;
    private String password;
}
