package com.sciatta.openmall.service.pojo.query;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ShopCartAddServiceQuery
 */
@Data
public class ShopCartAddServiceQuery implements Serializable {
    private static final long serialVersionUID = -350908879281100647L;
    
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer buyCounts;
    private String priceDiscount;
    private String priceNormal;
}
