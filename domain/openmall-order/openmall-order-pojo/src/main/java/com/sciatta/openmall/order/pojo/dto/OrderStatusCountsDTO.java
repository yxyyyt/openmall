package com.sciatta.openmall.order.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderStatusCountsDTO
 */
@Data
public class OrderStatusCountsDTO implements Serializable {
    
    private static final long serialVersionUID = -8530778901703295100L;
    
    private Integer waitPayCounts;
    private Integer waitDeliverCounts;
    private Integer waitReceiveCounts;
    private Integer waitCommentCounts;
}
