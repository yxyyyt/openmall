package com.sciatta.openmall.dao.pojo.po.ext;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ShopCartItem
 */
@Data
public class ShopCartItem implements Serializable {
    
    private static final long serialVersionUID = -7062234530048766278L;
    
    private String itemId;
    private String itemName;
    private String itemImgUrl;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;
}
