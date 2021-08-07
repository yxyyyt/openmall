package com.sciatta.openmall.dao.pojo.po.ext;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/7<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * SearchItem
 */
@Data
public class SearchItem implements Serializable {
    private static final long serialVersionUID = 4810106174116115942L;
    
    private String itemId;
    private String itemName;
    private int sellCounts;
    private String imgUrl;
    private int price;
}
