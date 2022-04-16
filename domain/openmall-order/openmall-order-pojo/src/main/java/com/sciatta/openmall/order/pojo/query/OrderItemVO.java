package com.sciatta.openmall.order.pojo.query;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderItemVO
 */
@Data
public class OrderItemVO implements Serializable {
    
    private static final long serialVersionUID = 3097880233222973233L;
    
    private String id;
    private String orderId;
    private String itemId;
    private String itemImg;
    private String itemName;
    private String itemSpecId;
    private String itemSpecName;
    private Integer price;
    private Integer buyCounts;
}