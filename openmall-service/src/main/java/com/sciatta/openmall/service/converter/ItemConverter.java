package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.dao.pojo.po.mbg.Item;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemImage;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemParam;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemSpec;
import com.sciatta.openmall.service.pojo.dto.ItemDTO;
import com.sciatta.openmall.service.pojo.dto.ItemImageDTO;
import com.sciatta.openmall.service.pojo.dto.ItemParamDTO;
import com.sciatta.openmall.service.pojo.dto.ItemSpecDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemConverter
 */
@Mapper
public abstract class ItemConverter {
    public static ItemConverter INSTANCE = Mappers.getMapper(ItemConverter.class);
    
    public abstract ItemDTO itemToItemDTO(Item item);
    
    public abstract List<ItemImageDTO> itemImagesToItemImagesDTO(List<ItemImage> itemImages);
    
    public abstract List<ItemSpecDTO> itemSpecsToItemSpecsDTO(List<ItemSpec> itemSpecs);
    
    public abstract ItemParamDTO itemParamToItemParamDTO(ItemParam itemParam);
}
