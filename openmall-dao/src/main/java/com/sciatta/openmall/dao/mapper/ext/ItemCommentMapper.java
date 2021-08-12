package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.ext.UserItemComment;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemCommentMapper extends com.sciatta.openmall.dao.mapper.mbg.ItemCommentMapper {
    List<UserItemComment> selectItemComments(@Param("itemId") String itemId, @Param("level") Integer level);
    
    Integer selectCommentCountsByItemIdAndLevel(@Param("itemId") String itemId, @Param("level") Integer level);
    
    int insertBatch(@Param("itemCommentList")List<ItemComment> itemCommentList);
}