package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/8/2<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemFlatteningDTO
 */
@Data
public class ItemFlatteningDTO implements Serializable {
    
    private static final long serialVersionUID = -7317265156860817339L;
    
    private String id;
    private String itemName;
    private Date createdTime;
    private String url;
}
