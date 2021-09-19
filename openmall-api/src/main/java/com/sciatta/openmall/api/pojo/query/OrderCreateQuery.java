package com.sciatta.openmall.api.pojo.query;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderCreateQuery
 */
@Data
public class OrderCreateQuery implements Serializable {
    
    private static final long serialVersionUID = 5345810943670031697L;
    
    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;
}
