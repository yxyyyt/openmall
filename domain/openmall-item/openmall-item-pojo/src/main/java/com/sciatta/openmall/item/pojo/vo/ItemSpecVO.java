package com.sciatta.openmall.item.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ItemSpecVO implements Serializable {

    private static final long serialVersionUID = 2438100782525511121L;

    private String id;
    private String itemId;
    private String name;
    private Integer stock;
    private BigDecimal discounts;
    private Integer priceDiscount;
    private Integer priceNormal;
}