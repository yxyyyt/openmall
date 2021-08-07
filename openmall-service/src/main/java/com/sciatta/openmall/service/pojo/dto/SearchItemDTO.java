package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/7<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * SearchItemDTO
 */
@Data
public class SearchItemDTO implements Serializable {
    
    private static final long serialVersionUID = 5280139178681601996L;
    
    private String itemId;
    private String itemName;
    private int sellCounts;
    private String imgUrl;
    private int price;
}
