package com.sciatta.openmall.service.pojo.query;

import lombok.Data;

import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/8/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserServiceQuery
 */
@Data
public class UserServiceQuery {
    private String nickname;
    private String realname;
    private Integer sex;
    private Date birthday;
    private String mobile;
    private String email;
    private String face;
}
