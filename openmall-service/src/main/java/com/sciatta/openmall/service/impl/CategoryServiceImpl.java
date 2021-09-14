package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.dao.mapper.ext.CategoryMapper;
import com.sciatta.openmall.dao.pojo.po.ext.Category;
import com.sciatta.openmall.service.CategoryService;
import com.sciatta.openmall.service.converter.CategoryConverter;
import com.sciatta.openmall.service.pojo.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CategoryServiceImpl
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
    
    @Override
    public List<CategoryDTO> queryAllRootLevel() {
        List<Category> categoryList = categoryMapper.selectByType(1); // 第一级商品种类
        
        return CategoryConverter.INSTANCE.convert(categoryList);
    }
    
    @Override
    public List<CategoryDTO> querySubCategoriesByParentId(Integer parentId) {
        // 通过一级商品种类Id查询二级和三级商品种类
        List<Category> subCategoryList = categoryMapper.selectSubCategoriesByParentId(parentId);
        
        return CategoryConverter.INSTANCE.convert(subCategoryList);
    }
    
    @Override
    public List<CategoryDTO> querySixItemsByParentId(Integer parentId) {
        List<Category> categoryList = categoryMapper.selectSixItemsByParentId(parentId);
        
        return CategoryConverter.INSTANCE.convert(categoryList);
    }
}
