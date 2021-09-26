package com.sciatta.openmall.dao.pojo.query;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderStatusQuery
 */
@Data
public class OrderStatusQuery implements Serializable {
    private static final long serialVersionUID = 5545919201198130860L;
    
    private String userId;
    private Integer isDelete;
    private List<Integer> orderStatuses;
}
