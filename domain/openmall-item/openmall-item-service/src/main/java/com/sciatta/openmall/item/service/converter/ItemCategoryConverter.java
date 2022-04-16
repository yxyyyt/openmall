package com.sciatta.openmall.item.service.converter;

import com.sciatta.openmall.item.pojo.dto.ItemCategoryDTO;
import com.sciatta.openmall.item.pojo.po.ext.ItemCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemCategoryConverter
 */
@Mapper
public abstract class ItemCategoryConverter {
    public static ItemCategoryConverter INSTANCE = Mappers.getMapper(ItemCategoryConverter.class);
    
    public abstract List<ItemCategoryDTO> toItemCategoryDTO(List<ItemCategory> itemCategoryList);
}
