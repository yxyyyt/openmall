package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.mbg.ItemImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemImageMapper extends com.sciatta.openmall.dao.mapper.mbg.ItemImageMapper {
    
    List<ItemImage> selectByItemId(@Param("itemId") String itemId);
}