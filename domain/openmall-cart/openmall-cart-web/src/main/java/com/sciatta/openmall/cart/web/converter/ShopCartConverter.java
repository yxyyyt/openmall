package com.sciatta.openmall.cart.web.converter;

import com.sciatta.openmall.item.pojo.dto.ItemDTO;
import com.sciatta.openmall.item.pojo.vo.ItemShopCartVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/9/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ShopCartConverter
 */
@Mapper
public abstract class ShopCartConverter {
    public static ShopCartConverter INSTANCE = Mappers.getMapper(ShopCartConverter.class);
    @Mappings({
            @Mapping(source = "id", target = "itemId"),
            @Mapping(source = "url", target = "itemImgUrl")
    })
    @Named(value = "ItemShopCartVO")
    public abstract ItemShopCartVO toItemShopCartVO(ItemDTO itemDTO);

    @IterableMapping(qualifiedByName = "ItemShopCartVO")
    public abstract List<ItemShopCartVO> toItemShopCartVO(List<ItemDTO> itemDTOList);
}
