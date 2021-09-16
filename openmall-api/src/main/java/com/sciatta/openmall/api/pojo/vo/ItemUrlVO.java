package com.sciatta.openmall.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/8/2<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemUrlVO
 */
@Data
public class ItemUrlVO implements Serializable {
    
    private static final long serialVersionUID = -7317265156860817339L;
    
    private String itemId;
    private String itemName;
    private String itemUrl;
}
