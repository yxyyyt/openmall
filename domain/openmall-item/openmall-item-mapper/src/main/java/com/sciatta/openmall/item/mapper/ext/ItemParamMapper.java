package com.sciatta.openmall.item.mapper.ext;

import com.sciatta.openmall.item.pojo.po.mbg.ItemParam;
import org.apache.ibatis.annotations.Param;

public interface ItemParamMapper extends com.sciatta.openmall.item.mapper.mbg.ItemParamMapper {
    ItemParam selectByItemId(@Param("itemId") String itemId);
}