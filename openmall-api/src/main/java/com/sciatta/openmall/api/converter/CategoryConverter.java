package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.vo.CategoryItemVO;
import com.sciatta.openmall.api.pojo.vo.CategorySubVO;
import com.sciatta.openmall.api.pojo.vo.CategoryVO;
import com.sciatta.openmall.api.pojo.vo.ItemUrlVO;
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
    
    public abstract List<CategoryVO> toCategoryVO(List<CategoryDTO> categoryDTOList);
    
    public abstract List<CategorySubVO> toCategorySubVO(List<CategoryDTO> categoryDTOList);
    
    // 只映射不同的属性
    @Mappings({
            @Mapping(source = "id", target = "itemId"),
            @Mapping(source = "url", target = "itemUrl")
    })
    // 自定义转换对象中的依赖
    public abstract ItemUrlVO convertToItemUrlVO(ItemDTO itemDTO);
    
    @Mappings({
            @Mapping(source = "name", target = "rootCatName")
    })
    // 自定义转换集合中的对象
    public abstract CategoryItemVO convertToCategoryItemVO(CategoryDTO categoryDTO);
    
    // 集合中的对象如果属性完全相同的话，就不需要映射
    public abstract List<CategoryItemVO> toCategoryItemVO(List<CategoryDTO> categoryDTOList);
}
