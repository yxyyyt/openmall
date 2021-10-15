package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.dao.pojo.po.ext.Item;
import com.sciatta.openmall.dao.pojo.po.ext.ItemComment;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemImage;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemParam;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemSpec;
import com.sciatta.openmall.service.pojo.dto.*;
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
    
    public abstract ItemDTO toItemDTO(Item item);
    
    public abstract List<ItemDTO> toItemDTO(List<Item> itemList);
    
    public abstract List<ItemDTO> searchToItemDTO(List<com.sciatta.openmall.search.pojo.po.Item> itemList);
    
    public abstract List<ItemImageDTO> toItemImageDTO(List<ItemImage> itemImageList);
    
    public abstract List<ItemSpecDTO> toItemSpecDTO(List<ItemSpec> itemSpecList);
    
    public abstract ItemParamDTO toItemParamDTO(ItemParam itemParam);
    
    public abstract List<ItemCommentDTO> toItemCommentDTO(List<ItemComment> itemCommentList);
    
    public abstract ItemCommentLevelCountDTO toItemCommentLevelCountDTO(Integer goodCounts, Integer normalCounts, Integer badCounts, Integer totalCounts);
}
