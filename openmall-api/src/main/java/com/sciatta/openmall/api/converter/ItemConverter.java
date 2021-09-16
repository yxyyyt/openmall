package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.vo.*;
import com.sciatta.openmall.service.pojo.dto.*;
import com.sciatta.openmall.service.pojo.query.UserItemCommentServiceQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
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
    public abstract ItemWrapVO convert(ItemDTO itemDTO, List<ItemImageDTO> itemImageDTOList,
                                       List<ItemSpecDTO> itemSpecDTOList, ItemParamDTO itemParamDTO);
    
    public abstract UserItemCommentServiceQuery toUserItemCommentServiceQuery(String itemId, Integer level);
    
    @Mappings({
            @Mapping(source = "itemSpecName", target = "specName"),
            @Mapping(expression = "java(com.sciatta.openmall.common.utils.DesensitizationUtils.commonDisplay(itemCommentDTO.getNickname()))", target = "nickname")
    })
    public abstract ItemCommentUserVO userItemCommentDTOToUserItemCommentVO(ItemCommentDTO itemCommentDTO);
    
    public abstract List<ItemCommentUserVO> convertToItemCommentUserVO(List<ItemCommentDTO> itemCommentDTOList);
    
    public abstract ItemCommentLevelCountVO convert(ItemCommentLevelCountDTO itemCommentLevelCountDTO);
    
    @Mappings({
            @Mapping(source = "id", target = "itemId"),
            @Mapping(source = "url", target = "imgUrl"),
            @Mapping(source = "priceDiscount", target = "price")
    })
    public abstract ItemSearchVO convert(ItemDTO itemDTO);
    
    public abstract List<ItemSearchVO> convertToItemSearchVO(List<ItemDTO> itemDTOList);
    
    public abstract List<ShopCartItemVO> shopCartItemDTOListToShopCartItemVOList(List<ShopCartItemDTO> shopCartItemDTOList);
}
