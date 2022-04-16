package com.sciatta.openmall.search.web.converter;

import com.sciatta.openmall.item.pojo.dto.ItemDTO;
import com.sciatta.openmall.search.pojo.dto.ItemSearchDTO;
import com.sciatta.openmall.search.pojo.vo.ItemSearchVO;
import org.mapstruct.Mapper;
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

    public abstract ItemSearchVO toItemSearchVO(ItemSearchDTO itemSearchDTO);

    public abstract List<ItemSearchVO> toItemSearchVO(List<ItemSearchDTO> itemSearchDTOList);
}
