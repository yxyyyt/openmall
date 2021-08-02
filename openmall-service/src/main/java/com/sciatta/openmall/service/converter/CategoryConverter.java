package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.dao.pojo.po.ext.SubCategory;
import com.sciatta.openmall.dao.pojo.po.mbg.Category;
import com.sciatta.openmall.service.pojo.dto.CategoryDTO;
import com.sciatta.openmall.service.pojo.dto.SubCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CategoryConverter
 */
@Mapper
public abstract class CategoryConverter {
    public static CategoryConverter INSTANCE = Mappers.getMapper(CategoryConverter.class);
    
    public abstract CategoryDTO categoryToCategoryDTO(Category category);
    
    public abstract List<CategoryDTO> categoriesToCategoriesDTO(List<Category> categories);
    
    public abstract List<SubCategoryDTO> subCategoriesToSubCategoriesDTO(List<SubCategory> subCategories);
}
