package com.sciatta.openmall.order.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderItemDTO
 */
@Data
public class OrderItemDTO implements Serializable {
    private static final long serialVersionUID = 7498343315715410063L;
    
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