package com.sciatta.openmall.service.pojo.query;

import lombok.Data;

import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/7/30<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户请求
 */
@Data
public class UserQuery {
    private String username;
    private String password;
    private String nickname;
    private String realname;
    private Integer sex;
    private Date birthday;
    private String mobile;
    private String email;
    private String face;
}
