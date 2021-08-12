package com.sciatta.openmall.service.pojo.query;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderItemCommentServiceQuery
 */
@Data
public class OrderItemCommentServiceQuery implements Serializable {
    
    private static final long serialVersionUID = -4503423003778431346L;
    
    private String itemId;
    private String itemName;
    private String itemSpecId;
    private String itemSpecName;
    private Integer commentLevel;
    private String content;
}
