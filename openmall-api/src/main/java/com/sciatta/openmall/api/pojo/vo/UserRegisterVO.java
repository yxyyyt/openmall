package com.sciatta.openmall.api.pojo.vo;

import lombok.Data;

/**
 * Created by yangxiaoyu on 2021/7/30<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户注册VO
 */
@Data
public class UserRegisterVO {
    private String id;
    private String username;
    private String nickname;
    private String realname;
    private Integer sex;
}
