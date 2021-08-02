package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.CategoryDTO;
import com.sciatta.openmall.service.pojo.dto.SubCategoryDTO;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 商品分类服务
 */
public interface CategoryService {
    List<CategoryDTO> queryAllRootLevel();
    
    public List<SubCategoryDTO> querySubCategoriesByParentId(Integer parentId);
}
