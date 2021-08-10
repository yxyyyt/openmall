package com.sciatta.openmall.api.pojo.query;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserAddressAddApiQuery
 */
@Data
public class UserAddressAddApiQuery implements Serializable {
    
    private static final long serialVersionUID = -4649332773262766042L;
    
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;
}
