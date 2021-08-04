package com.sciatta.openmall.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemImageVO implements Serializable {
    
    private static final long serialVersionUID = 1630115923669963692L;
    
    private String id;
    private String itemId;
    private String url;
    private Integer sort;
    private Integer isMain;
}