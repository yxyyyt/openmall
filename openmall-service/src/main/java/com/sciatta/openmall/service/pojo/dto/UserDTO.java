package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户信息DTO
 */
@Data
public class UserDTO implements Serializable {
    
    private static final long serialVersionUID = -1039132031279962493L;
    
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
