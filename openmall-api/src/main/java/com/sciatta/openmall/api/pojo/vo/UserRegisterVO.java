package com.sciatta.openmall.api.pojo.vo;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/7/30<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户注册VO
 */
public class UserRegisterVO implements Serializable {
    
    private static final long serialVersionUID = -4390829132820573049L;
    
    private String id;
    private String username;
    private String nickname;
    private String realname;
    private Integer sex;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getRealname() {
        return realname;
    }
    
    public void setRealname(String realname) {
        this.realname = realname;
    }
    
    public Integer getSex() {
        return sex;
    }
    
    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
