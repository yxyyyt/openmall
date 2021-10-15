package com.sciatta.openmall.search.converter;

import com.sciatta.openmall.common.constants.SearchConstants;
import com.sciatta.openmall.search.pojo.po.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.elasticsearch.core.SearchHit;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemConverter
 */
@Mapper
public abstract class ItemConverter {
    public static ItemConverter INSTANCE = Mappers.getMapper(ItemConverter.class);
    
    public Item toItem(SearchHit<Item> searchHit) {
        Item item = searchHit.getContent();
        // 高亮替换
        item.setItemName(searchHit.getHighlightFields().get(SearchConstants.ITEM_NAME).get(0));
        
        return item;
    }
}
