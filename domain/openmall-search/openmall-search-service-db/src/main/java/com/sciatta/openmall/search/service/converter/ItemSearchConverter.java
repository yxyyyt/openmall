package com.sciatta.openmall.search.service.converter;

import com.sciatta.openmall.item.pojo.dto.ItemDTO;
import com.sciatta.openmall.search.pojo.dto.ItemSearchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemSearchConverter
 */
@Mapper
public abstract class ItemSearchConverter {
    public static ItemSearchConverter INSTANCE = Mappers.getMapper(ItemSearchConverter.class);

    public ItemSearchDTO toItemSearchDTO(HashMap<String, ?> itemDTO) {
        if (itemDTO == null) {
            return null;
        }

        ItemSearchDTO itemSearchDTO = new ItemSearchDTO();

        if (itemDTO.containsKey("id")) {
            itemSearchDTO.setItemId((String) itemDTO.get("id"));
        }
        if (itemDTO.containsKey("url")) {
            itemSearchDTO.setImgUrl((String) itemDTO.get("url"));
        }
        if (itemDTO.containsKey("itemName")) {
            itemSearchDTO.setItemName((String) itemDTO.get("itemName"));
        }
        if (itemDTO.containsKey("priceDiscount")) {
            itemSearchDTO.setPrice((Integer) itemDTO.get("priceDiscount"));
        }
        if (itemDTO.containsKey("sellCounts")) {
            itemSearchDTO.setSellCounts((Integer) itemDTO.get("sellCounts"));
        }

        return itemSearchDTO;
    }

    public List<ItemSearchDTO> toItemSearchDTO(List<HashMap<String, ?>> itemDTOList) {
        if (itemDTOList == null) {
            return null;
        }

        List<ItemSearchDTO> list = new ArrayList<ItemSearchDTO>(itemDTOList.size());
        for (HashMap<String, ?> hashMap : itemDTOList) {
            list.add(toItemSearchDTO(hashMap));
        }

        return list;
    }
}
