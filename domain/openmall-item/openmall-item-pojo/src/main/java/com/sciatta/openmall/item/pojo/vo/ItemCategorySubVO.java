package com.sciatta.openmall.item.pojo.vo;

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
public class ItemCategorySubVO implements Serializable {
    private static final long serialVersionUID = -1313390306547211213L;
    
    private Integer id;
    private String name;
    private Integer type;
    private Integer parentId;
    
    private List<ItemCategorySubVO> subCategories = new ArrayList<>();
}
