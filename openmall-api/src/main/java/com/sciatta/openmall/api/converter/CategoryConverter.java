package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.vo.CategoryItemVO;
import com.sciatta.openmall.api.pojo.vo.CategoryVO;
import com.sciatta.openmall.api.pojo.vo.ItemFlatteningVO;
import com.sciatta.openmall.api.pojo.vo.SubCategoryVO;
import com.sciatta.openmall.service.pojo.dto.CategoryDTO;
import com.sciatta.openmall.service.pojo.dto.CategoryItemDTO;
import com.sciatta.openmall.service.pojo.dto.ItemFlatteningDTO;
import com.sciatta.openmall.service.pojo.dto.SubCategoryDTO;
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
    
    public abstract CategoryVO categoryDTOToCategoryVO(CategoryDTO categoryDTO);
    
    public abstract List<CategoryVO> categoriesDTOToCategoriesVO(List<CategoryDTO> categoryDTO);
    
    public abstract List<SubCategoryVO> subCategoriesDTOToSubCategoriesVO(List<SubCategoryDTO> subCategoriesDTO);
    
    @Mappings({
            @Mapping(source = "id", target = "itemId"),
            @Mapping(source = "itemName", target = "itemName"),
            @Mapping(source = "url", target = "itemUrl")
    })
    public abstract ItemFlatteningVO itemFlatteningDTOToItemFlatteningVO(ItemFlatteningDTO itemFlatteningDTO);
    
    @Mappings({
            @Mapping(source = "name", target = "rootCatName"),
            @Mapping(source = "itemFlattenings", target = "simpleItemList")
    })
    public abstract CategoryItemVO categoryItemDTOToCategoryItemVO(CategoryItemDTO categoryItemDTO);
    
    public abstract List<CategoryItemVO> categoryItemsDTOToCategoryItemsVO(List<CategoryItemDTO> categoryItemsDTO);
}
