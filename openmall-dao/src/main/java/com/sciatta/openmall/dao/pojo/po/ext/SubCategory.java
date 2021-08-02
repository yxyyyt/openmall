package com.sciatta.openmall.dao.pojo.po.ext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/1<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * SubCategory
 */
public class SubCategory implements Serializable {
    private static final long serialVersionUID = -6838930528652832049L;
    
    private Integer id;
    private String name;
    private Integer type;
    private Integer parentId;
    
    private List<SubCategory> subCategories = new ArrayList<>();
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getParentId() {
        return parentId;
    }
    
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    public List<SubCategory> getSubCategories() {
        return subCategories;
    }
    
    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}
