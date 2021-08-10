package com.sciatta.openmall.service.pojo.query;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderCreateServiceQuery
 */
@Data
public class OrderCreateServiceQuery implements Serializable {
    
    private static final long serialVersionUID = -3242173960345060889L;
    
    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;
}
