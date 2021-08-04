package com.sciatta.openmall.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemVO
 */
@Data
public class ItemVO implements Serializable {
    
    private static final long serialVersionUID = -7215988390088371475L;
    
    private String id;
    private String itemName;
    private Integer catId;
    private Integer rootCatId;
    private Integer sellCounts;
    private Integer onOffStatus;
    private String content;
}
