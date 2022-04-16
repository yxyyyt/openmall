package com.sciatta.openmall.item.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemParamDTO implements Serializable {

    private static final long serialVersionUID = 3426700566643471547L;

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