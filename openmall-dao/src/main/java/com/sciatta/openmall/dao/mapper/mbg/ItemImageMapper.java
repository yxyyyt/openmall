package com.sciatta.openmall.dao.mapper.mbg;

import com.sciatta.openmall.dao.pojo.po.mbg.ItemImage;

public interface ItemImageMapper {
    int deleteByPrimaryKey(String id);

    int insert(ItemImage record);

    int insertSelective(ItemImage record);

    ItemImage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ItemImage record);

    int updateByPrimaryKey(ItemImage record);
}