package com.sciatta.openmall.dao.pojo.po.ext;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/2<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CategoryItem
 */
@Data
public class CategoryItem implements Serializable {
    private static final long serialVersionUID = -125497998935588766L;
    
    private Integer id;
    private String name;
    private String slogan;
    private String catImage;
    private String bgColor;
    
    private List<ItemFlattening> itemFlattenings = new ArrayList<>();
}
