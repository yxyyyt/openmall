package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.vo.CategoryItemVO;
import com.sciatta.openmall.api.pojo.vo.CategorySubVO;
import com.sciatta.openmall.api.pojo.vo.CategoryVO;
import com.sciatta.openmall.api.pojo.vo.ItemURLVO;
import com.sciatta.openmall.service.pojo.dto.CategoryDTO;
import com.sciatta.openmall.service.pojo.dto.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
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
    
    public abstract List<CategoryVO> convertToCategoryVOList(List<CategoryDTO> categoryDTOList);
    
    public abstract List<CategorySubVO> convertToCategorySubVOList(List<CategoryDTO> categoryDTOList);
    
    // 只映射不同的属性
    @Mappings({
            @Mapping(source = "id", target = "itemId"),
            @Mapping(source = "url", target = "itemUrl")
    })
    public abstract ItemURLVO convert(ItemDTO itemDTO); // 自定义转换对象中的依赖
    
    @Mappings({
            @Mapping(source = "name", target = "rootCatName")
    })
    public abstract CategoryItemVO convert(CategoryDTO categoryDTO);    // 自定义转换集合中的对象
    
    public abstract List<CategoryItemVO> convert(List<CategoryDTO> categoryDTOList);    // 集合中的对象如果属性完全相同的话，就不需要映射
}
