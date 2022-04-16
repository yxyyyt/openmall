package com.sciatta.openmall.item.mapper.mbg;

import com.sciatta.openmall.item.pojo.po.mbg.ItemParam;

public interface ItemParamMapper {
    int deleteByPrimaryKey(String id);

    int insert(ItemParam record);

    int insertSelective(ItemParam record);

    ItemParam selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ItemParam record);

    int updateByPrimaryKey(ItemParam record);
}