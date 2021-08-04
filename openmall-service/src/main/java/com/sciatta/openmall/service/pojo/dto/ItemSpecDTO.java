package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ItemSpecDTO implements Serializable {
    
    private static final long serialVersionUID = -3403158540690745570L;
    
    private String id;
    private String itemId;
    private String name;
    private Integer stock;
    private BigDecimal discounts;
    private Integer priceDiscount;
    private Integer priceNormal;
}