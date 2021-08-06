package com.sciatta.openmall.service;

import com.sciatta.openmall.common.utils.PagedUtils;
import com.sciatta.openmall.service.pojo.dto.*;
import com.sciatta.openmall.service.pojo.query.UserItemCommentServiceQuery;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemService
 */
public interface ItemService {
    ItemDTO queryItemById(String id);
    
    List<ItemImageDTO> queryItemImagesByItemId(String itemId);
    
    List<ItemSpecDTO> queryItemSpecsByItemId(String itemId);
    
    ItemParamDTO queryItemParamByItemId(String itemId);
    
    List<UserItemCommentDTO> queryUserItemComment(UserItemCommentServiceQuery userItemCommentServiceQuery, PagedUtils.PagedGridResult pagedGridResult);
    
    CommentLevelCountsDTO queryCommentLevelCounts(String itemId);
}
