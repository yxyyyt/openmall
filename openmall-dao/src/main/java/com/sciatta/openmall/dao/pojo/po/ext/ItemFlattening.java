package com.sciatta.openmall.dao.pojo.po.ext;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/8/2<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemFlattening
 */
@Data
public class ItemFlattening implements Serializable {
    private static final long serialVersionUID = 3051272797538556507L;
    
    private String id;  // Item
    private String itemName;    // Item
    private Date createdTime;   // Item
    private String url; // ItemImage
}
