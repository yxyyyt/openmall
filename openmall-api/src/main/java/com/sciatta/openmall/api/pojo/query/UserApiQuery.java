package com.sciatta.openmall.api.pojo.query;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/8/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserApiQuery
 */
@Data
public class UserApiQuery {
    
    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 12, message = "用户昵称不能超过12位")
    private String nickname;
    
    @Length(max = 12, message = "用户真实姓名不能超过12位")
    private String realname;
    
    @Min(value = 0, message = "性别选择不正确")
    @Max(value = 2, message = "性别选择不正确")
    private Integer sex;
    
    private Date birthday;
    
    @Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$", message = "手机号格式不正确")  // TODO delete +
    private String mobile;
    
    @Email
    private String email;
}
