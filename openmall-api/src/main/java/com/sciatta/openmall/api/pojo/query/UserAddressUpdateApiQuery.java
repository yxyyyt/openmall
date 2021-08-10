package com.sciatta.openmall.api.pojo.query;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserAddressUpdateApiQuery
 */
@Data
public class UserAddressUpdateApiQuery implements Serializable {
    
    private static final long serialVersionUID = 8062784332165340869L;
    
    private String id;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;
}
