package com.sciatta.openmall.user.pojo.query;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/7/16<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserQuery
 */
@Data
public class UserQuery implements Serializable {

    private static final long serialVersionUID = -1883196877494940480L;

    @NotBlank(message = "用户名不能为空", groups = {WebLogin.class, WebRegister.class, Api.class})
    @Length(max = 12, message = "用户名不能超过12位", groups = {WebLogin.class, WebRegister.class, Api.class})
    private String username;

    @NotBlank(message = "密码不能为空", groups = {WebLogin.class, WebRegister.class, Api.class})
    @Size(min = 6, message = "密码不能小于6位", groups = {WebLogin.class, WebRegister.class, Api.class})
    private String password;

    @NotBlank(message = "密码不能为空", groups = {WebRegister.class})
    @Size(min = 6, message = "密码不能小于6位", groups = {WebRegister.class})
    private String confirmPassword;

    @NotBlank(message = "用户昵称不能为空", groups = {WebUpdate.class, Api.class})
    @Length(max = 12, message = "用户昵称不能超过12位", groups = {WebUpdate.class, Api.class})
    private String nickname;

    @Length(max = 12, message = "用户真实姓名不能超过12位", groups = {WebUpdate.class, Api.class})
    @NotBlank(message = "用户真实姓名不能为空", groups = {WebUpdate.class, Api.class})
    private String realname;

    @Min(value = 0, message = "性别选择不正确", groups = {WebUpdate.class, Api.class})
    @Max(value = 2, message = "性别选择不正确", groups = {WebUpdate.class, Api.class})
    @NotNull(message = "性别不能为空", groups = {WebUpdate.class, Api.class})
    private Integer sex;

    @NotNull(message = "出生日期不能为空", groups = {Api.class})
    private Date birthday;

    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "手机号格式不正确", groups = {WebUpdate.class, Api.class})
    @NotNull(message = "手机号不能为空", groups = {WebUpdate.class, Api.class})
    private String mobile;

    @Email(message = "邮箱格式不正确", groups = {WebUpdate.class, Api.class})
    @NotNull(message = "邮箱不能为空", groups = {WebUpdate.class, Api.class})
    private String email;

    @NotNull(message = "头像不能为空", groups = {Api.class})
    private String face;

    public interface WebLogin {

    }

    public interface WebRegister {

    }

    public interface WebUpdate {

    }

    public interface Api {

    }
}
