package com.sciatta.openmall.item.mapper.mbg;

import com.sciatta.openmall.item.pojo.po.mbg.ItemImage;

public interface ItemImageMapper {
    int deleteByPrimaryKey(String id);

    int insert(ItemImage record);

    int insertSelective(ItemImage record);

    ItemImage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ItemImage record);

    int updateByPrimaryKey(ItemImage record);
}