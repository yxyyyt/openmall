package com.sciatta.openmall.item.mapper.mbg;

import com.sciatta.openmall.item.pojo.po.mbg.ItemComment;

public interface ItemCommentMapper {
    int deleteByPrimaryKey(String id);

    int insert(ItemComment record);

    int insertSelective(ItemComment record);

    ItemComment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ItemComment record);

    int updateByPrimaryKey(ItemComment record);
}