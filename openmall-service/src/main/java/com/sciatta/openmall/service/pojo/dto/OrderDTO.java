package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderDTO
 */
@Data
public class OrderDTO implements Serializable {
    
    private static final long serialVersionUID = -3650235366887826546L;
    
    private String orderId;
    private Integer isComment;
    private Integer isDelete;
    private MerchantOrderDTO merchantOrderDTO;
}
