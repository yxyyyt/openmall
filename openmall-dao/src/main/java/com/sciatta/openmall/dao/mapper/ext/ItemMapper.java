package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.ext.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper extends com.sciatta.openmall.dao.mapper.mbg.ItemMapper {
    
    Item selectByPrimaryKey(String id);
    
    List<Item> searchItemsByKeywords(@Param("keywords") String keywords, @Param("sort") String sort);
    
    List<Item> searchItemsByCatId(@Param("catId") Integer catId, @Param("sort") String sort);
    
    List<Item> searchShopCartItemsBySpecIds(List<String> specIds);
}