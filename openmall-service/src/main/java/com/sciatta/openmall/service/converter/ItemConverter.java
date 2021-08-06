package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.dao.pojo.po.ext.UserItemComment;
import com.sciatta.openmall.dao.pojo.po.mbg.Item;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemImage;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemParam;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemSpec;
import com.sciatta.openmall.dao.pojo.query.UserItemCommentDaoQuery;
import com.sciatta.openmall.service.pojo.dto.*;
import com.sciatta.openmall.service.pojo.query.UserItemCommentServiceQuery;
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
    
    public abstract List<ItemImageDTO> itemImageListToItemImageDTOList(List<ItemImage> itemImageList);
    
    public abstract List<ItemSpecDTO> itemSpecListToItemSpecDTOList(List<ItemSpec> itemSpecList);
    
    public abstract ItemParamDTO itemParamToItemParamDTO(ItemParam itemParam);
    
    public abstract UserItemCommentDaoQuery userItemCommentServiceQueryToUserItemCommentDaoQuery(
            UserItemCommentServiceQuery userItemCommentServiceQuery);
    
    public abstract List<UserItemCommentDTO> userItemCommentListToUserItemCommentDTOList(List<UserItemComment> userItemCommentList);
    
    public abstract CommentLevelCountsDTO toCommentLevelCountsDTO(Integer goodCounts, Integer normalCounts, Integer badCounts, Integer totalCounts);
}
