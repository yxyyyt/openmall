package com.sciatta.openmall.order.pojo.po.ext;

import com.sciatta.openmall.order.pojo.po.mbg.OrderItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/9/26<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderStatus
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class OrderStatus extends com.sciatta.openmall.order.pojo.po.mbg.OrderStatus implements Serializable {
    private static final long serialVersionUID = 3515837704669337387L;
    
    // Order
    private Integer payMethod;
    private Integer realPayAmount;
    private Integer postAmount;
    private Integer isComment;
    
    // OrderItem
    private List<OrderItem> subOrderItemList;
}
