package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.mbg.ItemParam;
import org.apache.ibatis.annotations.Param;

public interface ItemParamMapper extends com.sciatta.openmall.dao.mapper.mbg.ItemParamMapper {
    ItemParam selectByItemId(@Param("itemId") String itemId);
}