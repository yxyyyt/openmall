package com.sciatta.openmall.order.pojo.query;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderStatusCountsVO
 */
@Data
public class OrderStatusCountsVO implements Serializable {
    
    private static final long serialVersionUID = 1119466640428753973L;
    
    private Integer waitPayCounts;
    private Integer waitDeliverCounts;
    private Integer waitReceiveCounts;
    private Integer waitCommentCounts;
}
