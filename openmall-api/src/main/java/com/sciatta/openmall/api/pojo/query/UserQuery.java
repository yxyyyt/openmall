package com.sciatta.openmall.api.pojo.query;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/7/16<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserQuery
 */
@Data
public class UserQuery {
    @NotBlank(message = "用户名不能为空", groups = {Login.class, Register.class})
    @Length(max = 12, message = "用户名不能超过12位", groups = {Login.class, Register.class})
    private String username;
    
    @NotBlank(message = "密码不能为空", groups = {Login.class, Register.class})
    @Size(min = 6, message = "密码不能小于6位", groups = {Login.class, Register.class})
    private String password;
    
    @NotBlank(message = "密码不能为空", groups = {Register.class})
    @Size(min = 6, message = "密码不能小于6位", groups = {Register.class})
    private String confirmPassword;
    
    @NotBlank(message = "用户昵称不能为空", groups = {Update.class})
    @Length(max = 12, message = "用户昵称不能超过12位", groups = {Update.class})
    private String nickname;
    
    @Length(max = 12, message = "用户真实姓名不能超过12位", groups = {Update.class})
    @NotBlank(message = "用户真实姓名不能为空", groups = {Update.class})
    private String realname;
    
    @Min(value = 0, message = "性别选择不正确", groups = {Update.class})
    @Max(value = 2, message = "性别选择不正确", groups = {Update.class})
    @NotNull(message = "性别不能为空", groups = {Update.class})
    private Integer sex;
    
    private Date birthday;
    
    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "手机号格式不正确", groups = {Update.class})
    @NotNull(message = "手机号不能为空", groups = {Update.class})
    private String mobile;
    
    @Email(message = "邮箱格式不正确", groups = {Update.class})
    @NotNull(message = "邮箱不能为空", groups = {Update.class})
    private String email;
    
    public interface Login {
    
    }
    
    public interface Register {
    
    }
    
    public interface Update {
    
    }
}
