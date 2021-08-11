package com.sciatta.openmall.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
}
