package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.vo.ItemInfoVO;
import com.sciatta.openmall.service.pojo.dto.ItemDTO;
import com.sciatta.openmall.service.pojo.dto.ItemImageDTO;
import com.sciatta.openmall.service.pojo.dto.ItemParamDTO;
import com.sciatta.openmall.service.pojo.dto.ItemSpecDTO;
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
    public abstract ItemInfoVO toItemInfoVO(ItemDTO itemDTO, List<ItemImageDTO> itemImageDTOList, List<ItemSpecDTO> itemSpecDTOList
            , ItemParamDTO itemParamDTO);
}
