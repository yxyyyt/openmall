package com.sciatta.openmall.user.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/7/30<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserAddressVO
 */
@Data
public class UserAddressVO implements Serializable {

    private static final long serialVersionUID = -8862264398703647181L;

    private String id;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;
    private String extend;
    private Integer isDefault;
}