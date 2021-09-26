package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.vo.*;
import com.sciatta.openmall.service.pojo.dto.*;
import com.sciatta.openmall.service.pojo.query.ItemCommentQuery;
import org.mapstruct.*;
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
    
    @Mappings({
            @Mapping(source = "itemDTO", target = "item"),
            @Mapping(source = "itemImageDTOList", target = "itemImgList"),
            @Mapping(source = "itemSpecDTOList", target = "itemSpecList"),
            @Mapping(source = "itemParamDTO", target = "itemParams")
    })
    public abstract ItemWrapVO toItemWrapVO(ItemDTO itemDTO, List<ItemImageDTO> itemImageDTOList,
                                            List<ItemSpecDTO> itemSpecDTOList, ItemParamDTO itemParamDTO);
    
    public abstract ItemCommentQuery toUserItemCommentServiceQuery(String itemId, Integer level);
    
    @Mappings({
            @Mapping(source = "itemSpecName", target = "specName"),
            @Mapping(expression = "java(com.sciatta.openmall.common.utils.DesensitizationUtils.commonDisplay(itemCommentDTO.getNickname()))", target = "nickname")
    })
    public abstract ItemCommentUserVO userItemCommentDTOToUserItemCommentVO(ItemCommentDTO itemCommentDTO);
    
    public abstract List<ItemCommentUserVO> toItemCommentUserVO(List<ItemCommentDTO> itemCommentDTOList);
    
    public abstract ItemCommentLevelCountVO toItemCommentLevelCountVO(ItemCommentLevelCountDTO itemCommentLevelCountDTO);
    
    @Mappings({
            @Mapping(source = "id", target = "itemId"),
            @Mapping(source = "url", target = "imgUrl"),
            @Mapping(source = "priceDiscount", target = "price")
    })
    @Named(value = "ItemSearchVO")
    public abstract ItemSearchVO toItemSearchVO(ItemDTO itemDTO);
    
    @IterableMapping(qualifiedByName = "ItemSearchVO")
    public abstract List<ItemSearchVO> toItemSearchVO(List<ItemDTO> itemDTOList);
    
    @Mappings({
            @Mapping(source = "id", target = "itemId"),
            @Mapping(source = "url", target = "itemImgUrl")
    })
    @Named(value = "ItemShopCartVO")
    public abstract ItemShopCartVO toItemShopCartVO(ItemDTO itemDTO);
    
    @IterableMapping(qualifiedByName = "ItemShopCartVO")
    public abstract List<ItemShopCartVO> toItemShopCartVO(List<ItemDTO> itemDTOList);
}
