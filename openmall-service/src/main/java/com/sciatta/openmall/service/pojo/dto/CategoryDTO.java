package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 商品种类DTO
 */
@Data
public class CategoryDTO implements Serializable {
    
    private static final long serialVersionUID = 8385262944496750216L;
    
    private Integer id;
    private String name;
    private String logo;
}
