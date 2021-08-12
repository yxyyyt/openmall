package com.sciatta.openmall.service.pojo.dto;

import com.sciatta.openmall.dao.pojo.po.mbg.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderStatusItemDTO
 */
@Data
public class OrderStatusItemDTO implements Serializable {
    
    private static final long serialVersionUID = 6779906908718555297L;
    // Order
    private String orderId;
    private Integer payMethod;
    private Integer realPayAmount;
    private Integer postAmount;
    private Integer isComment;
    private Date createdTime;
    
    // OrderStatus
    private Integer orderStatus;
    
    // OrderItem
    private List<OrderItem> subOrderItemList;
}
