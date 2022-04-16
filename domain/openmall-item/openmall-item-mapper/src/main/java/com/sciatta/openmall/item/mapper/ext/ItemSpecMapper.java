package com.sciatta.openmall.item.mapper.ext;

import com.sciatta.openmall.item.pojo.po.mbg.ItemSpec;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemSpecMapper extends com.sciatta.openmall.item.mapper.mbg.ItemSpecMapper {
    List<ItemSpec> selectByItemId(@Param("itemId") String itemId);

    int decreaseItemSpecStock(@Param("id") String id, @Param("pendingCounts") Integer pendingCounts);
}