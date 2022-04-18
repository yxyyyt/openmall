package com.sciatta.openmall.search.service.converter;

import com.sciatta.openmall.search.pojo.dto.ItemSearchDTO;
import com.sciatta.openmall.search.service.pojo.po.ItemSearch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemSearchConverter
 */
@Mapper
public abstract class ItemSearchConverter {
    public static ItemSearchConverter INSTANCE = Mappers.getMapper(ItemSearchConverter.class);

    @Mappings({
            @Mapping(source = "id", target = "itemId"),
            @Mapping(source = "url", target = "imgUrl"),
            @Mapping(source = "priceDiscount", target = "price")
    })
    public abstract ItemSearchDTO toItemSearchDTO(ItemSearch itemSearch);

    public abstract List<ItemSearchDTO> toItemSearchDTO(List<ItemSearch> itemSearchList);
}
