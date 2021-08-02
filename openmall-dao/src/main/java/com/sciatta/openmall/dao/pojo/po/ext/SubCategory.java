package com.sciatta.openmall.dao.pojo.po.ext;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/1<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * SubCategory
 */
@Data
public class SubCategory implements Serializable {
    private static final long serialVersionUID = -6838930528652832049L;
    
    private Integer id;
    private String name;
    private Integer type;
    private Integer parentId;
    
    private List<SubCategory> subCategories = new ArrayList<>();
}
