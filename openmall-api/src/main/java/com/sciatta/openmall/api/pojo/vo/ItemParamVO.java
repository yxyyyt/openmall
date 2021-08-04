package com.sciatta.openmall.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemParamVO implements Serializable {
    
    private static final long serialVersionUID = -3340214584149389482L;
    
    private String id;
    private String itemId;
    private String productPlace;
    private String foodPeriod;
    private String brand;
    private String factoryName;
    private String factoryAddress;
    private String packagingMethod;
    private String weight;
    private String storageMethod;
    private String eatMethod;
}