package com.sciatta.openmall.user.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/7/30<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserCookieVO
 */
@Data
public class UserCookieVO implements Serializable {
    private static final long serialVersionUID = -5439348993419470007L;

    private String id;
    private String username;
    private String nickname;
    private String realname;
    private String face;
    private Integer sex;
    private String userUniqueToken;
}
