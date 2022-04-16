package com.sciatta.openmall.item.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemImageDTO implements Serializable {
    private static final long serialVersionUID = -6215383044744688028L;

    private String id;
    private String itemId;
    private String url;
    private Integer sort;
    private Integer isMain;
}