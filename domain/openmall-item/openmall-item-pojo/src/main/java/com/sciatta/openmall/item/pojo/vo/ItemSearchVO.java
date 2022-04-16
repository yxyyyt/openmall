package com.sciatta.openmall.item.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/7<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemSearchVO
 */
@Data
public class ItemSearchVO implements Serializable {

    private static final long serialVersionUID = -8978342781520812508L;

    // Item
    private String itemId;
    private String itemName;
    private int sellCounts;

    // ItemImage
    private String imgUrl;

    // ItemSpec
    private int price;
}
