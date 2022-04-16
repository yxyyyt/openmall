package com.sciatta.openmall.item.mapper.ext;

import com.sciatta.openmall.item.pojo.po.mbg.ItemImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemImageMapper extends com.sciatta.openmall.item.mapper.mbg.ItemImageMapper {
    
    List<ItemImage> selectByItemId(@Param("itemId") String itemId);
}