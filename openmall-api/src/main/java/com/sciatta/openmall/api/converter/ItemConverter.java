package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.vo.CommentLevelCountsVO;
import com.sciatta.openmall.api.pojo.vo.ItemInfoVO;
import com.sciatta.openmall.api.pojo.vo.UserItemCommentVO;
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
    public abstract ItemInfoVO toItemInfoVO(ItemDTO itemDTO, List<ItemImageDTO> itemImageDTOList,
                                            List<ItemSpecDTO> itemSpecDTOList, ItemParamDTO itemParamDTO);
    
    public abstract UserItemCommentServiceQuery toUserItemCommentServiceQuery(String itemId, Integer level,
                                                                              Integer page, Integer pageSize);
    
    @Mappings({
            @Mapping(expression = "java(com.sciatta.openmall.common.utils.DesensitizationUtils.commonDisplay(userItemCommentDTO.getNickname()))", target = "nickname")
    })
    public abstract UserItemCommentVO userItemCommentDTOToUserItemCommentVO(UserItemCommentDTO userItemCommentDTO);
    
    
    public abstract List<UserItemCommentVO> userItemCommentDTOListToUserItemCommentVOList(List<UserItemCommentDTO> userItemCommentDTOList);
    
    public abstract CommentLevelCountsVO commentLevelCountsDTOToCommentLevelCountsVO(CommentLevelCountsDTO commentLevelCountsDTO);
}
