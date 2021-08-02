package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户登录DTO
 */
@Data
public class UserLoginDTO implements Serializable {
    
    private static final long serialVersionUID = 8433145146502245050L;
    
    private String id;
    private String username;
    private String nickname;
    private String realname;
    private Integer sex;
}
