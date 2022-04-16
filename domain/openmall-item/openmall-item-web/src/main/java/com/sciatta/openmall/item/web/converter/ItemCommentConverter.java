package com.sciatta.openmall.item.web.converter;

import com.sciatta.openmall.item.pojo.dto.ItemCommentDTO;
import com.sciatta.openmall.item.pojo.vo.ItemCommentUrlVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemCommentConverter
 */
@Mapper
public abstract class ItemCommentConverter {
    public static ItemCommentConverter INSTANCE = Mappers.getMapper(ItemCommentConverter.class);
    
    @Mappings({
            @Mapping(source = "id", target = "commentId"),
            @Mapping(source = "itemSpecName", target = "specName"),
            @Mapping(source = "url", target = "itemImg")
    })
    public abstract ItemCommentUrlVO toItemCommentUrlVO(ItemCommentDTO itemCommentDTO);
    
    public abstract List<ItemCommentUrlVO> toItemCommentUrlVO(List<ItemCommentDTO> imageItemCommentDTOList);
}
