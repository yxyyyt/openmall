package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.dao.mapper.ext.CategoryMapper;
import com.sciatta.openmall.dao.pojo.po.ext.CategoryItem;
import com.sciatta.openmall.dao.pojo.po.ext.SubCategory;
import com.sciatta.openmall.dao.pojo.po.mbg.Category;
import com.sciatta.openmall.service.CategoryService;
import com.sciatta.openmall.service.converter.CategoryConverter;
import com.sciatta.openmall.service.pojo.dto.CategoryDTO;
import com.sciatta.openmall.service.pojo.dto.CategoryItemDTO;
import com.sciatta.openmall.service.pojo.dto.SubCategoryDTO;
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
        
        return CategoryConverter.INSTANCE.categoryListToCategoryDTOList(categoryList);
    }
    
    @Override
    public List<SubCategoryDTO> querySubCategoriesByParentId(Integer parentId) {
        
        List<SubCategory> subCategoryList = categoryMapper.selectSubCategoriesByParentId(parentId);   // 通过一级商品种类Id查询二级和三级商品种类
        
        return CategoryConverter.INSTANCE.subCategoryListToSubCategoryDTOList(subCategoryList);
    }
    
    @Override
    public List<CategoryItemDTO> querySixItemsByParentId(Integer parentId) {
        List<CategoryItem> categoryItemList = categoryMapper.selectSixItemsByParentId(parentId);
        
        return CategoryConverter.INSTANCE.categoryItemListToCategoryItemDTOList(categoryItemList);
    }
    
}
