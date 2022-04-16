package com.sciatta.openmall.item.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemShopCartVO
 */
@Data
public class ItemShopCartVO implements Serializable {

    private static final long serialVersionUID = -3830630139835202763L;

    private String itemId;
    private String itemName;
    private String itemImgUrl;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;
}
