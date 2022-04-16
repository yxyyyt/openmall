package com.sciatta.openmall.user.pojo.query;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserAddressQuery
 */
@Data
public class UserAddressQuery implements Serializable {

    private static final long serialVersionUID = -4649332773262766042L;

    @NotBlank(message = "用户地址主键不能为空", groups = {WebUpdate.class, Api.class})
    private String id;

    @NotBlank(message = "用户标识不能为空", groups = {WebAdd.class, WebUpdate.class, Api.class})
    private String userId;

    @NotBlank(message = "收件人姓名不能为空", groups = {WebAdd.class, WebUpdate.class, Api.class})
    @Length(max = 12, message = "收件人姓名不能超过12位", groups = {WebAdd.class, WebUpdate.class, Api.class})
    private String receiver;

    @NotBlank(message = "收件人手机号不能为空", groups = {WebAdd.class, WebUpdate.class, Api.class})
    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "收件人手机号格式不正确", groups = {WebAdd.class, WebUpdate.class, Api.class})
    private String mobile;

    @NotBlank(message = "省份不能为空", groups = {WebAdd.class, WebUpdate.class, Api.class})
    private String province;

    @NotBlank(message = "城市不能为空", groups = {WebAdd.class, WebUpdate.class, Api.class})
    private String city;

    @NotBlank(message = "区县不能为空", groups = {WebAdd.class, WebUpdate.class, Api.class})
    private String district;

    @NotBlank(message = "详细地址不能为空", groups = {WebAdd.class, WebUpdate.class, Api.class})
    private String detail;

    public interface WebAdd {

    }

    public interface WebUpdate {

    }

    public interface Api {

    }
}
