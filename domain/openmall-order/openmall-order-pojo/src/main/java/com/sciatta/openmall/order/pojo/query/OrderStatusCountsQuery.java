package com.sciatta.openmall.order.pojo.query;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderStatusCountsQuery
 */
@Data
public class OrderStatusCountsQuery implements Serializable {
    
    private static final long serialVersionUID = 6914207747084440368L;
    
    private String userId;
    private Integer orderStatus;
    private Integer isComment;
}
