package com.sciatta.openmall.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;

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