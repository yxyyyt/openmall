package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddressDTO implements Serializable {
    
    private static final long serialVersionUID = -7021178583638540272L;
    
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