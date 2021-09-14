package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.dao.pojo.po.ext.Category;
import com.sciatta.openmall.service.pojo.dto.CategoryDTO;
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
    
    public abstract List<CategoryDTO> convert(List<Category> categoryList);
}
