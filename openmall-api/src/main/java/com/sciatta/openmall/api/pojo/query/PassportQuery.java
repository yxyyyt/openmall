package com.sciatta.openmall.api.pojo.query;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by yangxiaoyu on 2021/7/16<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * PassportQuery，包括分组验证信息
 */
@Data
public class PassportQuery {
    @NotBlank(message = "用户名不能为空", groups = {Login.class, Register.class})
    @Length(max = 12, message = "用户名不能超过12位", groups = {Login.class, Register.class})
    private String username;
    
    @NotBlank(message = "密码不能为空", groups = {Login.class, Register.class})
    @Size(min = 6, message = "密码不能小于6位", groups = {Login.class, Register.class})
    private String password;
    
    @NotBlank(message = "密码不能为空", groups = {Register.class})
    @Size(min = 6, message = "密码不能小于6位", groups = {Register.class})
    private String confirmPassword;
    
    public interface Login {
    
    }
    
    public interface Register {
    
    }
}
