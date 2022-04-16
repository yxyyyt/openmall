package com.sciatta.openmall.search.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/7<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemSearchDTO
 */
@Data
public class ItemSearchDTO implements Serializable {

    private static final long serialVersionUID = -2728720286767160228L;
    // Item
    private String itemId;
    private String itemName;
    private Integer sellCounts;

    // ItemImage
    private String imgUrl;

    // ItemSpec
    private Integer price;
}
