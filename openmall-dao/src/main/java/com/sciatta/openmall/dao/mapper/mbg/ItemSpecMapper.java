package com.sciatta.openmall.dao.mapper.mbg;

import com.sciatta.openmall.dao.pojo.po.mbg.ItemSpec;

public interface ItemSpecMapper {
    int deleteByPrimaryKey(String id);

    int insert(ItemSpec record);

    int insertSelective(ItemSpec record);

    ItemSpec selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ItemSpec record);

    int updateByPrimaryKey(ItemSpec record);
}