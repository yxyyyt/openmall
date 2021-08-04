package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.mbg.ItemSpec;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemSpecMapper extends com.sciatta.openmall.dao.mapper.mbg.ItemSpecMapper {
    List<ItemSpec> selectByItemId(@Param("itemId") String itemId);
}