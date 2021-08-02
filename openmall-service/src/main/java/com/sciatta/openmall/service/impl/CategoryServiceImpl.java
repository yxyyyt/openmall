package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.dao.mapper.ext.CategoryMapper;
import com.sciatta.openmall.dao.pojo.po.ext.SubCategory;
import com.sciatta.openmall.dao.pojo.po.mbg.Category;
import com.sciatta.openmall.service.CategoryService;
import com.sciatta.openmall.service.converter.CategoryConverter;
import com.sciatta.openmall.service.pojo.dto.CategoryDTO;
import com.sciatta.openmall.service.pojo.dto.SubCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CategoryServiceImpl
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    
    @Override
    public List<CategoryDTO> queryAllRootLevel() {
        List<Category> categories = categoryMapper.selectByType(1); // 第一级商品种类
        
        return CategoryConverter.INSTANCE.categoriesToCategoriesDTO(categories);
    }
    
    @Override
    public List<SubCategoryDTO> querySubCategoriesByParentId(Integer parentId) {
        
        List<SubCategory> subCategories = categoryMapper.selectSubCategoriesByParentId(parentId);   // 通过一级商品种类Id查询二级和三级商品种类
        
        return CategoryConverter.INSTANCE.subCategoriesToSubCategoriesDTO(subCategories);
    }
    
}
