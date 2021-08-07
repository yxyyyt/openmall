package com.sciatta.openmall.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/7<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * SearchItemVO
 */
@Data
public class SearchItemVO implements Serializable {
    
    private static final long serialVersionUID = -8978342781520812508L;
    
    private String itemId;
    private String itemName;
    private int sellCounts;
    private String imgUrl;
    private int price;
}
