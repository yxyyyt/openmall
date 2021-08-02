package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 子商品种类DTO，描述二级和三级分类
 */
@Data
public class SubCategoryDTO implements Serializable {
    
    private static final long serialVersionUID = 8385262944496750216L;
    
    private Integer id;
    private String name;
    private Integer type;
    private Integer parentId;
    
    private List<SubCategoryDTO> subCategories = new ArrayList<>();
}
