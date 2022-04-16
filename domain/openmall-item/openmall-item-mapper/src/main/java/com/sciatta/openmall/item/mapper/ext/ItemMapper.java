package com.sciatta.openmall.item.mapper.ext;

import com.sciatta.openmall.item.pojo.po.ext.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper extends com.sciatta.openmall.item.mapper.mbg.ItemMapper {
    
    Item selectByPrimaryKey(String id);
    
    List<Item> searchItemsByKeywords(@Param("keywords") String keywords, @Param("sort") String sort);
    
    List<Item> searchItemsByCatId(@Param("catId") Integer catId, @Param("sort") String sort);
    
    List<Item> searchItemsBySpecIds(List<String> specIds);
}