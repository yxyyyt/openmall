package com.sciatta.openmall.user.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserVO
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1312961520733588311L;

    private String id;
    private String username;
    private String nickname;
    private String realname;
    private String face;
    private Integer sex;
    private Date birthday;
    private String mobile;
    private String email;
}
