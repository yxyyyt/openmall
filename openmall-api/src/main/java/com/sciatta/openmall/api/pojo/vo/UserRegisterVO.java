package com.sciatta.openmall.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/7/30<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户注册VO
 */
@Data
public class UserRegisterVO implements Serializable {
    private static final long serialVersionUID = -2177867295758908481L;
    
    private String id;
    private String username;
    private String nickname;
    private String realname;
    private Integer sex;
}
