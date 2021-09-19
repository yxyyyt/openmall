package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemDTO
 */
@Data
public class ItemDTO implements Serializable {
    private static final long serialVersionUID = -1058139665502712958L;
    
    // Item
    private String id;
    private String itemName;
    private Integer catId;
    private Integer rootCatId;
    private Integer sellCounts;
    private Integer onOffStatus;
    private String content;
    private Date createdTime;
    
    // ItemImage
    private String url;
    
    // ItemSpec
    private String specId;
    private String specName;
    private Integer priceDiscount;
    private Integer priceNormal;
}
