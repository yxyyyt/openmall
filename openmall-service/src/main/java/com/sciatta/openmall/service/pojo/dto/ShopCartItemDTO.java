package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ShopCartItemDTO
 */
@Data
public class ShopCartItemDTO implements Serializable {
    
    private static final long serialVersionUID = -469706288844167023L;
    
    private String itemId;
    private String itemName;
    private String itemImgUrl;
    private String specId;
    private String specName;
    private String priceDiscount;
    private String priceNormal;
}
