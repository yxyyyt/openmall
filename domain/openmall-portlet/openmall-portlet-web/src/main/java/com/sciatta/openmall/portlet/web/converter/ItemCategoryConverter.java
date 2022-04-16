package com.sciatta.openmall.portlet.web.converter;

import com.sciatta.openmall.item.pojo.dto.ItemCategoryDTO;
import com.sciatta.openmall.item.pojo.dto.ItemDTO;
import com.sciatta.openmall.item.pojo.vo.ItemCategorySubVO;
import com.sciatta.openmall.item.pojo.vo.ItemCategoryVO;
import com.sciatta.openmall.item.pojo.vo.ItemCategoryWithItemVO;
import com.sciatta.openmall.item.pojo.vo.ItemUrlVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
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
    
    public abstract List<ItemCategoryVO> toItemCategoryVO(List<ItemCategoryDTO> itemCategoryDTOList);
    
    public abstract List<ItemCategorySubVO> toItemCategorySubVO(List<ItemCategoryDTO> itemCategoryDTOList);
    
    // 只映射不同的属性
    @Mappings({
            @Mapping(source = "id", target = "itemId"),
            @Mapping(source = "url", target = "itemUrl")
    })
    // 自定义转换对象中的依赖
    public abstract ItemUrlVO toItemUrlVO(ItemDTO itemDTO);
    
    @Mappings({
            @Mapping(source = "name", target = "rootCatName")
    })
    // 自定义转换集合中的对象
    public abstract ItemCategoryWithItemVO toItemCategoryWithItemVO(ItemCategoryDTO itemCategoryDTO);
    
    // 集合中的对象如果属性完全相同的话，就不需要映射
    public abstract List<ItemCategoryWithItemVO> toItemCategoryWithItemVO(List<ItemCategoryDTO> itemCategoryDTOList);
}
