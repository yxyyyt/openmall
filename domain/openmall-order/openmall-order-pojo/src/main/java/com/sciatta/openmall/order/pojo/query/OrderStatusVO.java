package com.sciatta.openmall.order.pojo.query;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderStatusVO
 */
@Data
public class OrderStatusVO implements Serializable {
    private static final long serialVersionUID = 5986346567171791566L;
    
    private String orderId;
    private Integer orderStatus;
    private Date createdTime;
    private Date payTime;
    private Date deliverTime;
    private Date successTime;
    private Date closeTime;
    private Date commentTime;
    
    // Order
    private Integer payMethod;
    private Integer realPayAmount;
    private Integer postAmount;
    private Integer isComment;
    
    private List<OrderItemVO> subOrderItemList;
}
