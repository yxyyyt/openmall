package com.sciatta.openmall.item.mapper.ext;

import com.sciatta.openmall.item.pojo.po.ext.ItemCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemCategoryMapper extends com.sciatta.openmall.item.mapper.mbg.ItemCategoryMapper {
    List<ItemCategory> selectByType(@Param("type") Integer type);

    List<ItemCategory> selectSubCategoriesByParentId(@Param("parentId") Integer parentId);

    List<ItemCategory> selectSixItemsByParentId(@Param("parentId") Integer parentId);
}