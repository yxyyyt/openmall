package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private Integer type;
    private String logo;
    private String slogan;
    private String catImage;
    private String bgColor;
    private Integer parentId;
    
    private List<CategoryDTO> subCategories = new ArrayList<>();
    
    private List<ItemDTO> items = new ArrayList<>();
}
